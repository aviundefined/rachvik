package com.rachvik.rummy.converters;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.type.UserDefinedType;
import com.rachvik.rummy.entity.Card;
import com.rachvik.rummy.entity.Deck;
import lombok.val;

public class DeckToUdtValueConverter extends BaseObjectToUdtConverter<Deck> {

  public DeckToUdtValueConverter(final CqlSession session, final String keyspaceName) {
    super(session, keyspaceName);
  }

  @Override
  public UdtValue convert(final Deck source) {
    val metadata = session.getMetadata();
    UserDefinedType userDefinedType =
        metadata
            .getKeyspace(keyspaceName) // Replace with your keyspace name
            .flatMap(ks -> ks.getUserDefinedType("deck"))
            .orElseThrow(() -> new IllegalStateException("User-defined type 'deck' not found"));

    return userDefinedType.newValue().setList("card", source.getCard(), Card.class);
  }
}
