package com.rachvik.cards;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.rachvik.games.cards.models.Card;
import com.rachvik.games.cards.models.CardValue;
import com.rachvik.games.cards.models.Deck;
import com.rachvik.games.cards.models.Suit;
import lombok.val;
import org.junit.jupiter.api.Test;

class DeckHelperTest {

  @Test
  public void testValidDeck() {
    // Creating a valid deck with 52 cards, each suit has all card values from ACE to KING
    val helper = new DeckHelper();
    val validDeck = helper.createNormalDeck(1);
    assertDoesNotThrow(() -> helper.isValidRegularDeck(validDeck));
  }

  @Test
  public void testValidDeckWithJokers() {
    // Creating a valid deck with 52 cards, each suit has all card values from ACE to KING
    val helper = new DeckHelper();
    val validDeck = helper.createDeck(1, 2);
    assertDoesNotThrow(() -> helper.isValidDeck(validDeck, 2));
  }

  @Test
  public void testInvalidDeckMissingCardValues() {
    // Creating an invalid deck where a suit is missing some card values
    val invalidDeck = createInvalidDeck();
    val helper = new DeckHelper();
    assertThrows(RuntimeException.class, () -> helper.isValidRegularDeck(invalidDeck));
  }

  @Test
  public void testInvalidDeckWithJokers() {
    // Creating a valid deck with 52 cards, each suit has all card values from ACE to KING
    val helper = new DeckHelper();
    val deck = helper.createDeck(1, 2);
    assertThrows(RuntimeException.class, () -> helper.isValidDeck(deck, 3));
    assertThrows(RuntimeException.class, () -> helper.isValidDeck(deck, 1));
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
        if (value == CardValue.CARD_VALUE_UNKNOWN
            || value == CardValue.UNRECOGNIZED
            || value == CardValue.CARD_VALUE_JOKER) {
          continue;
        }
        Card.Builder cardBuilder = Card.newBuilder().setSuit(suit).setValue(value);
        deckBuilder.addCard(cardBuilder.build());
      }
    }

    return deckBuilder.build();
  }
}
