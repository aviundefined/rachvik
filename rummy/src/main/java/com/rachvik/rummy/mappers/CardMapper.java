package com.rachvik.rummy.mappers;

import com.rachvik.rummy.entity.Card;
import org.springframework.stereotype.Component;

@Component
public class CardMapper {

  public Card protoToEntity(com.rachvik.games.cards.models.Card card) {
    if (card == null) {
      return Card.builder().build();
    }
    return Card.builder()
        .deckIdentifier(card.getDeckIdentifier())
        .suit(card.getSuit())
        .cardValue(card.getValue())
        .build();
  }

  public com.rachvik.games.cards.models.Card entityToProto(Card card) {
    if (card == null) {
      return com.rachvik.games.cards.models.Card.newBuilder().build();
    }
    return com.rachvik.games.cards.models.Card.newBuilder()
        .setDeckIdentifier(card.getDeckIdentifier())
        .setSuit(card.getSuit())
        .setValue(card.getCardValue())
        .build();
  }
}
