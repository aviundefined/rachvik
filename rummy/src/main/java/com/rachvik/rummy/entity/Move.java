package com.rachvik.rummy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("move")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Move {
  @PrimaryKey private long id;

  @Column("game_id")
  private String gameId;

  @Column("timestamp")
  private long timestamp;

  @Column("username")
  private Player player;

  @Column("is_picked_from_discarded_pile")
  private boolean isPickedFromDiscardedPile;

  @Column("picked")
  private Card picked;

  @Column("discarded")
  private Card discarded;
}
