package com.rachvik.cards;

import com.rachvik.games.cards.models.CardValue;
import com.rachvik.games.cards.models.Deck;
import com.rachvik.games.cards.models.Suit;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
public class DeckValidator {

  public void isValidNormalDeck(final Deck deck) {
    val suitCardValues = new EnumMap<Suit, Set<CardValue>>(Suit.class);

    // Initialize suitCardValues map with sets for each suit
    for (val suit : Suit.values()) {
      if (suit == Suit.SUIT_UNKNOWN || suit == Suit.UNRECOGNIZED) {
        continue;
      }
      suitCardValues.put(suit, new HashSet<>());
    }

    // Check for each card in the deck
    for (val card : deck.getCardList()) {
      val suit = card.getSuit();
      CardValue value = card.getValue();

      // Add the card value to its corresponding suit's set
      suitCardValues.get(suit).add(value);
    }

    // Check if each suit has all card values from ACE to KING
    for (val suit : suitCardValues.keySet()) {
      val cardValues = suitCardValues.get(suit);
      if (!cardValues.containsAll(getAllCardValues())) {
        throw new RuntimeException(
            "Invalid deck: Each suit should have all card values from ACE to KING");
      }
    }

    // Check if the total number of cards is 52
    if (deck.getCardCount() != 52) {
      throw new RuntimeException("Invalid deck: Total number of cards should be 52");
    }
  }

  private Set<CardValue> getAllCardValues() {
    val allCardValues = new HashSet<CardValue>();
    for (val value : CardValue.values()) {
      if (value == CardValue.CARD_VALUE_UNKNOWN || value == CardValue.UNRECOGNIZED) {
        continue;
      }
      allCardValues.add(value);
    }
    return allCardValues;
  }
}
