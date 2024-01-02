package com.rachvik.rummy.converters;

import com.datastax.oss.driver.api.core.data.UdtValue;
import com.rachvik.rummy.entity.Config;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class UdtValueToConfigConverter extends BaseUdtToObjectConverter<Config> {

  @Override
  public Config convert(UdtValue source) {
    return Config.builder()
        .minNumberOfPlayers(source.getInt("min_number_of_players"))
        .maxNumberOfPlayers(source.getInt("max_number_of_players"))
        .maxTimeoutMillisToStart(source.getInt("max_timeout_millis_to_start"))
        .build();
  }
}
