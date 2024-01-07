package com.rachvik.rummy.validator;

import com.rachvik.games.cards.models.GameState;
import com.rachvik.games.cards.rummy.models.RummyGame;
import com.rachvik.games.cards.rummy.models.RummyGameState;
import com.rachvik.games.cards.rummy.models.RummyGameState.Builder;
import com.rachvik.games.cards.rummy.models.RummyMove;
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

  public void validateJoinGameRequest(Builder gameState, RummyGame.Builder gameBuilder) {
    if (gameState.getPlayerCount() >= gameBuilder.getConfig().getMaxNumberOfPlayers()) {
      log.error(
          "Number of players already reached to max number of allowed player: {}",
          gameBuilder.getConfig().getMaxNumberOfPlayers());
      throw new RuntimeException(
          String.format(
              "Number of players already reached to max number of allowed player: %s",
              gameBuilder.getConfig().getMaxNumberOfPlayers()));
    }
  }

  public void validateMove(final RummyGame.Builder game, final RummyMove move) {
    if (game.getState().getState() != GameState.GAME_STATE_IN_PROGRESS) {
      throw new RuntimeException("Game not in progress so move can't be recorded");
    }
    if (game.getState().getActivePlayerIndex() == -1) {
      throw new RuntimeException("Active player index not set in game");
    }
    val activePlayer = game.getState().getPlayer(game.getState().getActivePlayerIndex());
    if (!activePlayer.getUsername().equals(move.getPlayer().getUsername())) {
      throw new RuntimeException(
          String.format(
              "Current active player: %s is not same as player: %s who requested move",
              activePlayer.getUsername(), move.getPlayer().getUsername()));
    }
  }
}
