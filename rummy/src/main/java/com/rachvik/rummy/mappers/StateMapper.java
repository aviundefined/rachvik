package com.rachvik.rummy.mappers;

import com.rachvik.games.cards.rummy.models.RummyGameState;
import com.rachvik.rummy.entity.State;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StateMapper {
  private final CardMapper cardMapper;
  private final PlayerMapper playerMapper;
  private final UserHandMapper userHandMapper;

  public State protoToEntity(final RummyGameState state) {
    if (state == null) {
      return State.builder().build();
    }
    return State.builder()
        .state(state.getState())
        .available(state.getAvailableList().stream().map(cardMapper::protoToEntity).toList())
        .joker(cardMapper.protoToEntity(state.getJoker()))
        .player(state.getPlayerList().stream().map(playerMapper::protoToEntity).toList())
        .userHand(state.getUserHandList().stream().map(userHandMapper::protoToEntity).toList())
        .discardedPile(
            state.getDiscardedPileList().stream().map(cardMapper::protoToEntity).toList())
        .numberOfTurnsPlayed(state.getNumberOfTurnsPlayed())
        .lastMoveId(state.getLastMoveId())
        .lastDiscardedCard(cardMapper.protoToEntity(state.getLastDiscardedCard()))
        .activePlayerIndex(state.getActivePlayerIndex())
        .build();
  }

  public RummyGameState entityToProto(State state) {
    if (state == null) {
      return RummyGameState.newBuilder().build();
    }
    return RummyGameState.newBuilder()
        .setState(state.getState())
        .addAllAvailable(state.getAvailable().stream().map(cardMapper::entityToProto).toList())
        .setJoker(cardMapper.entityToProto(state.getJoker()))
        .addAllPlayer(state.getPlayer().stream().map(playerMapper::entityToProto).toList())
        .addAllUserHand(state.getUserHand().stream().map(userHandMapper::entityToProto).toList())
        .addAllDiscardedPile(
            state.getDiscardedPile().stream().map(cardMapper::entityToProto).toList())
        .setNumberOfTurnsPlayed(state.getNumberOfTurnsPlayed())
        .setLastMoveId(state.getLastMoveId())
        .setLastDiscardedCard(cardMapper.entityToProto(state.getLastDiscardedCard()))
        .setActivePlayerIndex(state.getActivePlayerIndex())
        .build();
  }
}
