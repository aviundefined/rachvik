package com.rachvik.rummy.converters;

import com.datastax.oss.driver.api.core.data.UdtValue;
import com.rachvik.games.cards.models.CardValue;
import com.rachvik.games.cards.models.Suit;
import com.rachvik.rummy.entity.Card;
import lombok.NoArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class UdtValueToCardConverter extends BaseUdtToObjectConverter<Card> {

  @Override
  public Card convert(UdtValue source) {
    val deckIdentifier = source.getInt("deck_identifier");
    val suit = Suit.valueOf(source.getString("suit"));
    val cardValue = CardValue.valueOf(source.getString("card_value"));
    return new Card(deckIdentifier, suit, cardValue);
  }
}
