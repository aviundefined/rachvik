package com.rachvik.rummy.services;

import com.rachvik.games.cards.models.Card;
import com.rachvik.games.cards.rummy.models.RummyGame;
import com.rachvik.games.cards.rummy.models.RummyGameState;
import com.rachvik.games.cards.rummy.models.RummyMove;
import java.util.ArrayList;
import java.util.List;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
public class MoveResolver {

  public RummyGame.Builder resolve(
      final RummyGame.Builder game, final long moveId, final RummyMove move) {
    val updatedState = game.getState().toBuilder();
    // Remove picked card from appropriate pile
    val discarded = new ArrayList<>(updatedState.getDiscardedPileList());
    val available = new ArrayList<>(updatedState.getAvailableList());
    if (move.getIsPickedFromDiscardedPile()) {
      removeCardFromPile(discarded, move.getPicked());
    } else {
      removeCardFromPile(available, move.getPicked());
    }

    // Add discarded card to discarded_pile
    discarded.add(move.getDiscarded());
    // Set discarded card as last_discarded_car
    updatedState.clearLastDiscardedCard().setLastDiscardedCard(move.getDiscarded());

    // Modify user_hand for the player
    val updatedUserHands = new ArrayList<>(updatedState.getUserHandList());
    for (int i = 0; i < updatedUserHands.size(); i++) {
      val userHand = updatedUserHands.get(i);
      if (userHand.getPlayer().getUsername().equals(move.getPlayer().getUsername())) {
        val updatedUserHand = userHand.toBuilder();
        val cards = new ArrayList<>(updatedUserHand.getCardList());
        cards.remove(move.getDiscarded());
        cards.add(move.getPicked());
        updatedUserHand.clearCard().addAllCard(cards);
        updatedUserHands.set(i, updatedUserHand.build());
        break;
      }
    }
    setNextActivePlayer(move, updatedState);
    updatedState.clearAvailable().addAllAvailable(available);
    updatedState.clearDiscardedPile().addAllDiscardedPile(discarded);
    updatedState.clearUserHand().addAllUserHand(updatedUserHands);
    // Increment number_of_turns_played by 1
    updatedState.setNumberOfTurnsPlayed(updatedState.getNumberOfTurnsPlayed() + 1);
    // Update last_move_id
    updatedState.setLastMoveId(moveId);

    // Build the updated game
    return game.clearState().setState(updatedState);
  }

  private void setNextActivePlayer(final RummyMove move, final RummyGameState.Builder state) {
    int currentPlayerIdx = -1;
    for (int i = 0; i < state.getPlayerCount(); i++) {
      if (move.getPlayer().getUsername().equals(state.getPlayer(i).getUsername())) {
        currentPlayerIdx = i;
      }
    }
    int nextPlayerIdx = currentPlayerIdx + 1;
    if (nextPlayerIdx == state.getPlayerCount()) {
      nextPlayerIdx = 0;
    }
    state.setActivePlayerIndex(nextPlayerIdx);
  }

  private void removeCardFromPile(final List<Card> pile, final Card cardToRemove) {
    for (int i = pile.size() - 1; i >= 0; i--) {
      if (pile.get(i).equals(cardToRemove)) {
        pile.remove(i);
        break;
      }
    }
  }
}
