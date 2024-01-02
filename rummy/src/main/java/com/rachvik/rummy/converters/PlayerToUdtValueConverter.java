package com.rachvik.rummy.converters;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.type.UserDefinedType;
import com.rachvik.rummy.entity.Card;
import com.rachvik.rummy.entity.Player;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
public class PlayerToUdtValueConverter extends BaseObjectToUdtConverter<Player> {

  public PlayerToUdtValueConverter(final CqlSession session, final String keyspaceName) {
    super(session, keyspaceName);
  }

  @Override
  public UdtValue convert(Player source) {
    val metadata = session.getMetadata();
    val userDefinedType =
        metadata
            .getKeyspace(keyspaceName) // Replace with your keyspace name
            .flatMap(ks -> ks.getUserDefinedType("player"))
            .orElseThrow(() -> new IllegalStateException("User-defined type 'player' not found"));

    return userDefinedType.newValue().setString("username", source.getUsername());
  }
}
