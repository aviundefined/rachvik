package com.rachvik.rummy.mappers;

import com.rachvik.games.cards.rummy.models.RummyGameConfig;
import com.rachvik.rummy.entity.Config;
import org.springframework.stereotype.Component;

@Component
public class ConfigMapper {

  public Config protoToEntity(final RummyGameConfig config) {
    if (config == null) {
      return Config.builder().build();
    }
    return Config.builder()
        .minNumberOfPlayers(config.getMinNumberOfPlayers())
        .maxNumberOfPlayers(config.getMaxNumberOfPlayers())
        .maxTimeoutMillisToStart((int) config.getMaxTimeoutMillisToStart())
        .build();
  }

  public RummyGameConfig entityToProto(final Config config) {
    if (config == null) {
      return RummyGameConfig.newBuilder().build();
    }
    return RummyGameConfig.newBuilder()
        .setMinNumberOfPlayers(config.getMinNumberOfPlayers())
        .setMaxNumberOfPlayers(config.getMaxNumberOfPlayers())
        .setMaxTimeoutMillisToStart(config.getMaxTimeoutMillisToStart())
        .build();
  }
}
