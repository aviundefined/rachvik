package com.rachvik.rummy.config;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.type.UserDefinedType;
import com.rachvik.rummy.entity.Card;
import lombok.val;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CardToUdtValueConverter implements Converter<Card, UdtValue> {

  private final CqlSession session;
  private final String keyspaceName;

  public CardToUdtValueConverter(final CqlSession session, final String keyspaceName) {
    this.session = session;
    this.keyspaceName = keyspaceName;
  }

  @Override
  public UdtValue convert(Card source) {
    val metadata = session.getMetadata();
    UserDefinedType userDefinedType =
        metadata
            .getKeyspace(keyspaceName) // Replace with your keyspace name
            .flatMap(ks -> ks.getUserDefinedType("card"))
            .orElseThrow(() -> new IllegalStateException("User-defined type 'card' not found"));

    return userDefinedType
        .newValue()
        .setString("suit", source.getSuit())
        .setString("cardValue", source.getCardValue());
  }
}
