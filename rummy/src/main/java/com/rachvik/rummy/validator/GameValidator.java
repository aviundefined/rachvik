package com.rachvik.rummy.validator;

import com.rachvik.games.cards.models.GameState;
import com.rachvik.games.cards.models.Player;
import com.rachvik.games.cards.rummy.models.RummyGame;
import com.rachvik.games.cards.rummy.models.RummyGameState;
import com.rachvik.games.cards.rummy.models.RummyMove;
import com.rachvik.games.cards.rummy.services.RummyPickCardRequest;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GameValidator {

  public void validateGameStartRequest(
      final int numPlayers,
      final int minPlayers,
      final int maxPlayers,
      final RummyGameState.Builder gameState,
      final String gameId) {
    // Check if the conditions for starting the game are met
    if (numPlayers < minPlayers
        || numPlayers > maxPlayers
        || gameState.getState() != GameState.GAME_STATE_NOT_STARTED) {
      // Return an error or handle the condition where the game cannot start
      throw new RuntimeException("Invalid Game state: +" + gameId);
    }
  }

  public void validateJoinGameRequest(final RummyGame.Builder game) {
    if (game.getState().getPlayerCount() >= game.getConfig().getMaxNumberOfPlayers()) {
      log.error(
          "Number of players already reached to max number of allowed player: {}",
          game.getConfig().getMaxNumberOfPlayers());
      throw new RuntimeException(
          String.format(
              "Number of players already reached to max number of allowed player: %s",
              game.getConfig().getMaxNumberOfPlayers()));
    }
  }

  public void validateMove(final RummyGame.Builder game, final RummyMove move) {
    validateGameInProgress(game);
    validateCurrentActivePlayer(game, move.getPlayer());
  }

  public void validatePickCardRequest(
      final RummyGame.Builder game, final RummyPickCardRequest request) {
    validateGameInProgress(game);
    validateCurrentActivePlayer(game, request.getPlayer());
  }

  private void validateCurrentActivePlayer(final RummyGame.Builder game, final Player request) {
    val activePlayer = game.getState().getPlayer(game.getState().getActivePlayerIndex());
    if (!activePlayer.getUsername().equals(request.getUsername())) {
      throw new RuntimeException(
          String.format(
              "Current active player: %s is not same as player: %s who requested move",
              activePlayer.getUsername(), request.getUsername()));
    }
  }

  private void validateGameInProgress(final RummyGame.Builder game) {
    if (game.getState().getState() != GameState.GAME_STATE_IN_PROGRESS) {
      throw new RuntimeException("Game not in progress so move can't be recorded");
    }
    if (game.getState().getActivePlayerIndex() == -1) {
      throw new RuntimeException("Active player index not set in game");
    }
  }
}
