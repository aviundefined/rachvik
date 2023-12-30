package com.rachvik.cards;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.rachvik.games.cards.models.Card;
import com.rachvik.games.cards.models.CardValue;
import com.rachvik.games.cards.models.Deck;
import com.rachvik.games.cards.models.Suit;
import lombok.val;
import org.junit.jupiter.api.Test;

class DeckValidatorTest {

  @Test
  public void testValidDeck() {
    // Creating a valid deck with 52 cards, each suit has all card values from ACE to KING
    val validDeck = createValidDeck();
    val validator = new DeckValidator();
    assertDoesNotThrow(() -> validator.isValidNormalDeck(validDeck));
  }

  @Test
  public void testInvalidDeckMissingCardValues() {
    // Creating an invalid deck where a suit is missing some card values
    val invalidDeck = createInvalidDeck();
    val validator = new DeckValidator();
    assertThrows(RuntimeException.class, () -> validator.isValidNormalDeck(invalidDeck));
  }

  private Deck createValidDeck() {
    Deck.Builder deckBuilder = Deck.newBuilder();

    // Adding 52 cards, each suit has all card values from ACE to KING
    for (val suit : Suit.values()) {
      if (suit == Suit.SUIT_UNKNOWN || suit == Suit.UNRECOGNIZED) {
        continue;
      }
      for (CardValue value : CardValue.values()) {
        if (value == CardValue.CARD_VALUE_UNKNOWN || value == CardValue.UNRECOGNIZED) {
          continue;
        }
        val cardBuilder = Card.newBuilder().setSuit(suit).setValue(value);
        deckBuilder.addCard(cardBuilder.build());
      }
    }

    return deckBuilder.build();
  }

  private Deck createInvalidDeck() {
    Deck.Builder deckBuilder = Deck.newBuilder();

    // Creating a deck where one suit is missing some card values
    for (Suit suit : Suit.values()) {
      if (suit == Suit.SUIT_UNKNOWN || suit == Suit.UNRECOGNIZED) {
        continue;
      }
      if (suit != Suit.SUIT_CLUB) {
        // For example, skipping adding all card values to SUIT_CLUB
        continue;
      }
      for (CardValue value : CardValue.values()) {
        if (value == CardValue.CARD_VALUE_UNKNOWN || value == CardValue.UNRECOGNIZED) {
          continue;
        }
        Card.Builder cardBuilder = Card.newBuilder().setSuit(suit).setValue(value);
        deckBuilder.addCard(cardBuilder.build());
      }
    }

    return deckBuilder.build();
  }
}
