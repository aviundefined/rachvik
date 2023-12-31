package com.rachvik.rummy.config;

import com.datastax.oss.driver.api.core.data.UdtValue;
import com.rachvik.rummy.entity.Card;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UdtToCardValueConverter implements Converter<UdtValue, Card> {

  @Override
  public Card convert(UdtValue source) {
    String suit = source.getString("suit");
    String cardValue = source.getString("cardValue");
    return new Card(suit, cardValue);
  }
}
