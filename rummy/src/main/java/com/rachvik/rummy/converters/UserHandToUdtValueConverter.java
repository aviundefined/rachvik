package com.rachvik.rummy.converters;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.data.UdtValue;
import com.rachvik.rummy.entity.Card;
import com.rachvik.rummy.entity.Player;
import com.rachvik.rummy.entity.UserHand;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
public class UserHandToUdtValueConverter extends BaseObjectToUdtConverter<UserHand> {

  public UserHandToUdtValueConverter(final CqlSession session, final String keyspaceName) {
    super(session, keyspaceName);
  }

  @Override
  public UdtValue convert(UserHand source) {
    val metadata = session.getMetadata();
    val userDefinedType =
        metadata
            .getKeyspace(keyspaceName) // Replace with your keyspace name
            .flatMap(ks -> ks.getUserDefinedType("user_hand"))
            .orElseThrow(() -> new IllegalStateException("User-defined type 'card' not found"));

    return userDefinedType
        .newValue()
        .set("player", source.getPlayer(), Player.class)
        .setList("card", source.getCard(), Card.class);
  }
}
