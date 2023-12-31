package com.rachvik.rummy.mappers;

import com.rachvik.games.cards.rummy.models.RummyMove;
import com.rachvik.rummy.entity.Move;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MoveMapper {

  private final CardMapper cardMapper;

  public Move protoToEntity(final long id, final RummyMove move) {
    return Move.builder()
        .id(id)
        .gameId(move.getGameId())
        .isPickedFromDiscardedPile(move.getIsPickedFromDiscardedPile())
        .username(move.getPlayer().getUsername())
        .timestamp(move.getTimestamp())
        .picked(cardMapper.protoToEntity(move.getPicked()))
        .discarded(cardMapper.protoToEntity(move.getDiscarded()))
        .build();
  }
}
