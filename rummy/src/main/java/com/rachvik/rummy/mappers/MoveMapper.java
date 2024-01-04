package com.rachvik.rummy.mappers;

import com.rachvik.games.cards.rummy.models.RummyMove;
import com.rachvik.rummy.entity.Move;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MoveMapper {

  private final CardMapper cardMapper;
  private final PlayerMapper playerMapper;

  public Move protoToEntity(final long id, final RummyMove move) {
    return Move.builder()
        .id(id)
        .gameId(move.getGameId())
        .isPickedFromDiscardedPile(move.getIsPickedFromDiscardedPile())
        .player(playerMapper.protoToEntity(move.getPlayer()))
        .timestamp(move.getTimestamp())
        .picked(cardMapper.protoToEntity(move.getPicked()))
        .discarded(cardMapper.protoToEntity(move.getDiscarded()))
        .build();
  }
}
