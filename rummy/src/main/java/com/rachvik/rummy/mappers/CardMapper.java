package com.rachvik.rummy.mappers;

import com.rachvik.rummy.entity.Card;
import org.springframework.stereotype.Component;

@Component
public class CardMapper {

  public Card protoToEntity(com.rachvik.games.cards.models.Card card) {
    return Card.builder().suit(card.getSuit().name()).cardValue(card.getValue().name()).build();
  }
}
