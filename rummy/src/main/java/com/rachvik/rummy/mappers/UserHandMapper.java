package com.rachvik.rummy.mappers;

import com.rachvik.games.cards.models.UserHand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserHandMapper {
  private final PlayerMapper playerMapper;
  private final CardMapper cardMapper;

  public com.rachvik.rummy.entity.UserHand protoToEntity(final UserHand userHand) {
    if (userHand == null) {
      return com.rachvik.rummy.entity.UserHand.builder().build();
    }
    return com.rachvik.rummy.entity.UserHand.builder()
        .player(playerMapper.protoToEntity(userHand.getPlayer()))
        .card(userHand.getCardList().stream().map(cardMapper::protoToEntity).toList())
        .build();
  }

  public UserHand entityToProto(com.rachvik.rummy.entity.UserHand userHand) {
    if (userHand == null) {
      return UserHand.newBuilder().build();
    }
    return UserHand.newBuilder()
        .setPlayer(playerMapper.entityToProto(userHand.getPlayer()))
        .addAllCard(userHand.getCard().stream().map(cardMapper::entityToProto).toList())
        .build();
  }
}
