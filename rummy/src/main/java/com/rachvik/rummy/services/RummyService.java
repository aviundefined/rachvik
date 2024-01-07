package com.rachvik.rummy.services;

import com.rachvik.cards.DeckHelper;
import com.rachvik.clients.IdServiceGrpcClient;
import com.rachvik.common.utils.CollectionUtils;
import com.rachvik.games.cards.models.Card;
import com.rachvik.games.cards.models.GameState;
import com.rachvik.games.cards.models.UserHand;
import com.rachvik.games.cards.rummy.models.RummyGame;
import com.rachvik.games.cards.rummy.models.RummyGameState;
import com.rachvik.games.cards.rummy.services.RummyCreateGameRequest;
import com.rachvik.games.cards.rummy.services.RummyGameResponse;
import com.rachvik.games.cards.rummy.services.RummyJoinGameRequest;
import com.rachvik.games.cards.rummy.services.RummyMoveRequest;
import com.rachvik.games.cards.rummy.services.RummyStartGameRequest;
import com.rachvik.id.UniqueIdRequest;
import com.rachvik.rummy.mappers.GameMapper;
import com.rachvik.rummy.mappers.MoveMapper;
import com.rachvik.rummy.repository.GameRepository;
import com.rachvik.rummy.repository.MoveRepository;
import com.rachvik.rummy.validator.GameValidator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RummyService {

  private final MoveRepository moveRepository;
  private final MoveMapper moveMapper;
  private final IdServiceGrpcClient idServiceGrpcClient;
  private final MoveResolver moveResolver;
  private final GameRepository gameRepository;
  private final DeckHelper deckHelper;
  private final GameMapper gameMapper;
  private final GameValidator gameValidator;
  private final ConcurrentMap<String, RummyGame.Builder> games = new ConcurrentHashMap<>();

  public RummyGameResponse createGame(final RummyCreateGameRequest request) {
    val game = RummyGame.newBuilder();

    // Generate a UUID for game_id
    String gameId = UUID.randomUUID().toString();
    game.setGameId(gameId);

    // Set RummyGameConfig
    game.setConfig(request.getConfig());

    // Use the decks to create RummyDeck object
    val state = RummyGameState.newBuilder();
    // Set State of the Game NOT STARTED
    state.setState(GameState.GAME_STATE_NOT_STARTED);
    // Create two decks using DeckHelper
    val deck1 = deckHelper.createDeck(1, 2); // Two jokers for deckIdentifier 1
    val deck2 = deckHelper.createDeck(2, 2); // Two jokers for deckIdentifier 2
    val availableCards = new ArrayList<>(deck1.getCardList());
    availableCards.addAll(deck2.getCardList());
    val random = new Random();
    // Select a random card as the joker
    val joker =
        deck1
            .getCardList()
            .get(random.nextInt(deck1.getCardCount() - 1)); // Assuming the first card is a joker
    state.setJoker(joker);
    removeCard(availableCards, joker);
    // Randomly select one card and add it to the discarded pile
    val discardedCard =
        deck2.getCardList().get(random.nextInt(deck2.getCardCount() - 1)); // randomly
    state.addDiscardedPile(discardedCard);
    removeCard(availableCards, discardedCard);
    Collections.shuffle(availableCards);
    state.addAllAvailable(availableCards);
    //  Initialize player, user_hand, and closed_piles as empty
    // These are repeated fields, so they are empty by default

    // Set number_of_turns_played to 0
    state.setNumberOfTurnsPlayed(0);
    // Update Joker and First Discarded card in used
    // Update last discarded card state as
    state.clearLastDiscardedCard().setLastDiscardedCard(discardedCard);
    // last_move_id will be unset as it's not specified in the logic
    game.setState(state);
    this.games.put(game.getGameId(), game);
    saveGame(game);
    return RummyGameResponse.newBuilder().setGame(game).build();
  }

  public RummyGameResponse joinGame(final RummyJoinGameRequest request) {
    val gameId = request.getGameId();
    val gameBuilder = getGame(gameId);
    val gameState = gameBuilder.getStateBuilder();
    gameValidator.validateJoinGameRequest(gameBuilder);
    // Add the player in RummyGameState
    val player = request.getPlayer();
    gameState.addPlayer(player);

    // Pick randomly 13 cards which are not present in used cards in RummyGameState
    val availableCards = new ArrayList<>(gameState.getAvailableList());
    val chosenCards = new ArrayList<Card>();
    val random = new Random();
    var cardsToChoose = 13;
    while (cardsToChoose > 0 && CollectionUtils.isNonEmpty(availableCards)) {
      int index = random.nextInt(availableCards.size());
      Card chosenCard = availableCards.remove(index);
      chosenCards.add(chosenCard);
      cardsToChoose--;
    }
    // Remove all the chosen cards from available cards
    availableCards.removeAll(chosenCards);
    gameState.clearAvailable().addAllAvailable(availableCards);
    // Update RummyUserHand for the current player with the randomly chosen 13 cards
    val userHand = UserHand.newBuilder().setPlayer(player).addAllCard(chosenCards);
    gameState.addUserHand(userHand);
    saveGame(gameBuilder);
    // Assuming this method builds the response based on the gameBuilder
    return RummyGameResponse.newBuilder().setGame(gameBuilder).build();
  }

  public RummyGameResponse startGame(final RummyStartGameRequest request) {
    val gameId = request.getGameId();
    val gameBuilder = getGame(gameId);

    val gameState = gameBuilder.getState().toBuilder();
    val numPlayers = gameState.getPlayerCount();
    val gameConfig = gameBuilder.getConfig();
    val minPlayers = gameConfig.getMinNumberOfPlayers();
    val maxPlayers = gameConfig.getMaxNumberOfPlayers();

    gameValidator.validateGameStartRequest(numPlayers, minPlayers, maxPlayers, gameState, gameId);
    // Set the game state to GAME_STATE_IN_PROGRESS
    gameState.setState(GameState.GAME_STATE_IN_PROGRESS);

    // Pick any random index between 0 to Size of Player - 1 from RummyGameState
    val activePlayerIndex = new Random().nextInt(gameState.getPlayerCount());
    // Set the randomly chosen index as active_player_index
    gameState.setActivePlayerIndex(activePlayerIndex);

    // Update the state in the game
    gameBuilder.setState(gameState);
    saveGame(gameBuilder);
    return RummyGameResponse.newBuilder().setGame(gameBuilder).build();
  }

  public RummyGameResponse recordMove(final RummyMoveRequest request) {
    if (!request.hasMove()) {
      throw new RuntimeException("Invalid move request: " + request);
    }
    val gameBuilder = getGame(request.getMove().getGameId());
    gameValidator.validateMove(gameBuilder, request.getMove());
    val id =
        idServiceGrpcClient
            .getStub()
            .getUniqueId(
                UniqueIdRequest.newBuilder().setServiceName(request.getMove().getGameId()).build())
            .getId();
    this.games.put(
        request.getMove().getGameId(), moveResolver.resolve(gameBuilder, id, request.getMove()));
    val move = moveMapper.protoToEntity(id, request.getMove());
    moveRepository.save(move);
    saveGame(gameBuilder);
    return RummyGameResponse.newBuilder()
        .setGame(this.games.get(request.getMove().getGameId()))
        .build();
  }

  private RummyGame.Builder getGame(final String gameId) {
    val builder = this.games.get(gameId);
    if (builder == null) {
      val game =
          this.gameRepository
              .findById(gameId)
              .orElseThrow(() -> new RuntimeException("Game not found: " + gameId));
      return gameMapper.entityToProto(game).toBuilder();
    }
    return builder;
  }

  private static void removeCard(final List<Card> cards, final Card cardToBeRemoved) {
    for (int i = cards.size() - 1; i >= 0; i--) {
      if (cards.get(i).equals(cardToBeRemoved)) {
        cards.remove(i);
        break;
      }
    }
  }

  private void saveGame(RummyGame.Builder game) {
    try {
      this.gameRepository.save(this.gameMapper.protoToEntity(game));
    } catch (final Exception e) {
      log.error("Error in saving game. ", e);
    }
  }
}
