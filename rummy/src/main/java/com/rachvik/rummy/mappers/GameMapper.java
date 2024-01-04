package com.rachvik.rummy.mappers;

import com.rachvik.games.cards.rummy.models.RummyGame;
import com.rachvik.rummy.entity.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameMapper {

  private final StateMapper stateMapper;
  private final ConfigMapper configMapper;

  public Game protoToEntity(RummyGame.Builder game) {
    return Game.builder()
        .gameId(game.getGameId())
        .state(stateMapper.protoToEntity(game.getState()))
        .config(configMapper.protoToEntity(game.getConfig()))
        .build();
  }

  public RummyGame entityToProto(final Game game) {
    return RummyGame.newBuilder()
        .setGameId(game.getGameId())
        .setState(stateMapper.entityToProto(game.getState()))
        .setConfig(configMapper.entityToProto(game.getConfig()))
        .build();
  }
}
