package com.rachvik.rummy.mappers;

import com.rachvik.games.cards.models.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {

  public com.rachvik.rummy.entity.Player protoToEntity(final Player player) {
    if (player == null) {
      return com.rachvik.rummy.entity.Player.builder().build();
    }
    return com.rachvik.rummy.entity.Player.builder().username(player.getUsername()).build();
  }
}
