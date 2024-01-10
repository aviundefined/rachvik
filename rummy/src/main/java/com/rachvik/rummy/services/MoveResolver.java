package com.rachvik.rummy.services;

import com.rachvik.games.cards.models.Card;
import com.rachvik.games.cards.rummy.models.RummyGame;
import com.rachvik.games.cards.rummy.models.RummyGameState;
import com.rachvik.games.cards.rummy.models.RummyMove;
import com.rachvik.games.cards.rummy.services.RummyPickCardRequest;
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

  public void pickCard(final RummyGame.Builder game, final RummyPickCardRequest request) {
    // If in RummyPickCardRequest -> pick_from_discard_pile is true then pick the last discarded
    // add last_discarded_card into player hand. if that's false then pick the first card from
    // available card and add in user hand. this will be intermediate state as player hand will
    // have 14 cards. so don't update the available cards and last discard card now that will be
    // update in when user would record its move. That's done on purpose if user drops in between
    // then last discard and available card state would be retained.
    val updatedUserHands = new ArrayList<>(game.getState().getUserHandList());
    for (int i = 0; i < updatedUserHands.size(); i++) {
      if (updatedUserHands
          .get(i)
          .getPlayer()
          .getUsername()
          .equals(request.getPlayer().getUsername())) {
        val currentUserHand = updatedUserHands.get(i).toBuilder();
        val cards = new ArrayList<>(currentUserHand.getCardList());
        if (request.getPickFromDiscardPile()) {
          cards.add(game.getState().getLastDiscardedCard());
        } else {
          cards.add(game.getState().getAvailable(0));
        }
        currentUserHand.clearCard().addAllCard(cards);
        updatedUserHands.set(i, currentUserHand.build());
        break;
      }
    }
    game.getStateBuilder().clearUserHand().addAllUserHand(updatedUserHands);
  }
}
