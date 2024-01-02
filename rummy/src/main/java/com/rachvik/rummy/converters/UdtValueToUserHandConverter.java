package com.rachvik.rummy.converters;

import com.datastax.oss.driver.api.core.data.UdtValue;
import com.rachvik.rummy.entity.Card;
import com.rachvik.rummy.entity.Player;
import com.rachvik.rummy.entity.UserHand;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class UdtValueToUserHandConverter extends BaseUdtToObjectConverter<UserHand> {

  @Override
  public UserHand convert(UdtValue source) {
    return UserHand.builder()
        .player(source.get("player", Player.class))
        .card(source.getList("card", Card.class))
        .build();
  }
}
