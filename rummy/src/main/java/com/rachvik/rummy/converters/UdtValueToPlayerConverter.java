package com.rachvik.rummy.converters;

import com.datastax.oss.driver.api.core.data.UdtValue;
import com.rachvik.rummy.entity.Player;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class UdtValueToPlayerConverter extends BaseUdtToObjectConverter<Player> {

  @Override
  public Player convert(UdtValue source) {

    return Player.builder().username(source.getString("username")).build();
  }
}
