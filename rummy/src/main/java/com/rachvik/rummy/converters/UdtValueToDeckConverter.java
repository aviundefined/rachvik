package com.rachvik.rummy.converters;

import com.datastax.oss.driver.api.core.data.UdtValue;
import com.rachvik.rummy.entity.Card;
import com.rachvik.rummy.entity.Deck;
import lombok.NoArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class UdtValueToDeckConverter extends BaseUdtToObjectConverter<Deck> {

  @Override
  public Deck convert(UdtValue source) {
    val cards = source.getList("card", Card.class);
    return Deck.builder().card(cards).build();
  }
}
