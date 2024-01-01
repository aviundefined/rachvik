package com.rachvik.rummy.services;

import com.rachvik.common.utils.CollectionUtils;
import com.rachvik.games.cards.models.Card;
import com.rachvik.games.cards.rummy.models.RummyGame;
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
    if (move.getIsPickedFromDiscardedPile()) {
      removeCardFromPile(
          CollectionUtils.emptyIfNull(updatedState.getDiscardedPileList()), move.getPicked());
    } else {
      removeCardFromPile(
          CollectionUtils.emptyIfNull(updatedState.getClosedPilesList()), move.getPicked());
    }

    // Add discarded card to discarded_pile
    updatedState.addDiscardedPile(move.getDiscarded());

    // Modify user_hand for the player
    val updatedUserHands = new ArrayList<>(updatedState.getUserHandList());
    for (int i = 0; i < updatedUserHands.size(); i++) {
      val userHand = updatedUserHands.get(i);
      if (userHand.getPlayer().getUsername().equals(move.getPlayer().getUsername())) {
        val updatedUserHand = userHand.toBuilder();
        updatedUserHand.getCardList().remove(move.getDiscarded());
        updatedUserHand.addCard(move.getPicked());
        updatedUserHands.set(i, updatedUserHand.build());
        break;
      }
    }
    updatedState.clearUserHand().addAllUserHand(updatedUserHands);

    // Increment number_of_turns_played by 1
    updatedState.setNumberOfTurnsPlayed(updatedState.getNumberOfTurnsPlayed() + 1);

    // Update last_move_id
    updatedState.setLastMoveId(moveId);

    // Build the updated game
    return game.clearState().setState(updatedState);
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
