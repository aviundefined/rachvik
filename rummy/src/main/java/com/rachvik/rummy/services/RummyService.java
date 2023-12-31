package com.rachvik.rummy.services;

import com.rachvik.cards.DeckHelper;
import com.rachvik.clients.IdServiceGrpcClient;
import com.rachvik.games.cards.models.GameState;
import com.rachvik.games.cards.rummy.models.RummyDeck;
import com.rachvik.games.cards.rummy.models.RummyGame;
import com.rachvik.games.cards.rummy.models.RummyGameState;
import com.rachvik.games.cards.rummy.services.RummyCreateGameRequest;
import com.rachvik.games.cards.rummy.services.RummyGameResponse;
import com.rachvik.games.cards.rummy.services.RummyMoveRequest;
import com.rachvik.games.cards.rummy.services.RummyStartGameRequest;
import com.rachvik.id.UniqueIdRequest;
import com.rachvik.rummy.mappers.MoveMapper;
import com.rachvik.rummy.repository.MoveRepository;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RummyService {

  private final MoveRepository moveRepository;
  private final MoveMapper moveMapper;
  private final IdServiceGrpcClient idServiceGrpcClient;
  private final MoveResolver moveResolver;
  private final DeckHelper deckHelper;
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
    state.setDeck(RummyDeck.newBuilder().addDeck(deck1).addDeck(deck2));

    // Select a random card as the joker
    // Assuming deck1 contains jokers, you can modify this based on your logic
    val joker = deck1.getCardList().get(5); // Assuming the first card is a joker
    state.setJoker(joker);

    // Randomly select one card and add it to the discarded pile
    // Assuming deck1 contains cards, you can modify this based on your logic
    val discardedCard = deck2.getCardList().get(10); // randomly
    state.addDiscardedPile(discardedCard);

    //  Initialize player, user_hand, and closed_piles as empty
    // These are repeated fields, so they are empty by default

    // Set number_of_turns_played to 0
    state.setNumberOfTurnsPlayed(0);

    // last_move_id will be unset as it's not specified in the logic
    game.setState(state);
    this.games.put(game.getGameId(), game);
    return RummyGameResponse.newBuilder().setGame(game).build();
  }

  public RummyGameResponse startGame(final RummyStartGameRequest request) {
    return null;
  }

  public RummyGameResponse recordMove(final RummyMoveRequest request) {
    if (!request.hasMove()) {
      throw new RuntimeException("Invalid move request: " + request);
    }
    val id =
        idServiceGrpcClient
            .getStub()
            .getUniqueId(
                UniqueIdRequest.newBuilder().setServiceName(request.getMove().getGameId()).build())
            .getId();
    val gameBuilder =
        this.games.computeIfAbsent(request.getMove().getGameId(), k -> RummyGame.newBuilder());
    this.games.put(
        request.getMove().getGameId(), moveResolver.resolve(gameBuilder, id, request.getMove()));
    val move = moveMapper.protoToEntity(id, request.getMove());
    moveRepository.save(move);
    return RummyGameResponse.newBuilder()
        .setGame(this.games.get(request.getMove().getGameId()))
        .build();
  }
}
