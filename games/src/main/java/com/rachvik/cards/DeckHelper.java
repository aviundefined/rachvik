package com.rachvik.cards;

import com.rachvik.games.cards.models.Card;
import com.rachvik.games.cards.models.CardValue;
import com.rachvik.games.cards.models.Deck;
import com.rachvik.games.cards.models.Deck.Builder;
import com.rachvik.games.cards.models.Suit;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
public class DeckHelper {

  public static final int TOTAL_NUMBER_OF_CARDS_IN_NORMAL_DECK = 52;

  public void isValidRegularDeck(final Deck deck) {
    isValidDeckInternal(deck);
    // Check if the total number of cards is 52
    if (deck.getCardCount() != TOTAL_NUMBER_OF_CARDS_IN_NORMAL_DECK) {
      throw new RuntimeException(
          "Invalid deck: Total number of cards should be: " + TOTAL_NUMBER_OF_CARDS_IN_NORMAL_DECK);
    }
  }

  public void isValidDeck(final Deck deck, final int numberOfJokers) {
    isValidDeckInternal(deck);
    if (deck.getCardCount() != 52 + numberOfJokers) {
      throw new RuntimeException(
          String.format(
              "Invalid deck: Total number of cards should be %s and %s additional jokers",
              TOTAL_NUMBER_OF_CARDS_IN_NORMAL_DECK, numberOfJokers));
    }
    int totalJokers = 0;
    for (val card : deck.getCardList()) {
      if (card.getValue() == CardValue.CARD_VALUE_JOKER) {
        totalJokers++;
      }
    }
    if (totalJokers != numberOfJokers) {
      throw new RuntimeException(
          String.format(
              "Invalid deck: Total number of jokers (%s) should be equal to %s",
              totalJokers, numberOfJokers));
    }
  }

  public Deck createNormalDeck(final int deckIdentifier) {
    val deckBuilder = createNormalDeckInternal(deckIdentifier);
    return deckBuilder.build();
  }

  public Deck createDeck(final int deckIdentifier, final int numberOfJokers) {
    val deckBuilder = createNormalDeckInternal(deckIdentifier);
    for (int i = 0; i < numberOfJokers; i++) {
      deckBuilder.addCard(
          Card.newBuilder().setDeckIdentifier(deckIdentifier).setValue(CardValue.CARD_VALUE_JOKER));
    }
    return deckBuilder.build();
  }

  private void isValidDeckInternal(Deck deck) {
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
      if (value == CardValue.CARD_VALUE_JOKER) {
        continue;
      }
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
  }

  private static Builder createNormalDeckInternal(int deckIdentifier) {
    val deckBuilder = Deck.newBuilder();
    // Adding 52 cards, each suit has all card values from ACE to KING
    for (val suit : Suit.values()) {
      if (suit == Suit.SUIT_UNKNOWN || suit == Suit.UNRECOGNIZED) {
        continue;
      }
      for (val value : CardValue.values()) {
        if (value == CardValue.CARD_VALUE_UNKNOWN
            || value == CardValue.UNRECOGNIZED
            || value == CardValue.CARD_VALUE_JOKER) {
          continue;
        }
        deckBuilder.addCard(
            Card.newBuilder().setDeckIdentifier(deckIdentifier).setSuit(suit).setValue(value));
      }
    }
    return deckBuilder;
  }

  private Set<CardValue> getAllCardValues() {
    val allCardValues = new HashSet<CardValue>();
    for (val value : CardValue.values()) {
      if (value == CardValue.CARD_VALUE_UNKNOWN
          || value == CardValue.UNRECOGNIZED
          || value == CardValue.CARD_VALUE_JOKER) {
        continue;
      }
      allCardValues.add(value);
    }
    return allCardValues;
  }
}
