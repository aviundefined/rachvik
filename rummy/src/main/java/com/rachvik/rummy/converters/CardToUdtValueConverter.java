package com.rachvik.rummy.converters;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.type.UserDefinedType;
import com.rachvik.rummy.entity.Card;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
public class CardToUdtValueConverter extends BaseObjectToUdtConverter<Card> {

  public CardToUdtValueConverter(final CqlSession session, final String keyspaceName) {
    super(session, keyspaceName);
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
        .setInt("deck_identifier", source.getDeckIdentifier())
        .setString("suit", source.getSuit().name())
        .setString("cardValue", source.getCardValue().name());
  }
}
