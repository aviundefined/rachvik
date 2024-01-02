package com.rachvik.rummy.mappers;

import com.rachvik.games.cards.rummy.models.RummyGame;
import com.rachvik.rummy.entity.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameMapper {

  private final StateMapper stateMapper;

  public Game protoToEntity(RummyGame.Builder game) {
    return Game.builder().state(stateMapper.protoToEntity(game.getState())).build();
  }
}
