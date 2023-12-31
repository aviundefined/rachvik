package com.rachvik.rummy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
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

  private long timestamp;
  private String username;

  @Column("is_picked_from_discarded_pile")
  private boolean isPickedFromDiscardedPile;

  @CassandraType(type = Name.UDT, userTypeName = "card")
  private Card picked;

  @CassandraType(type = Name.UDT, userTypeName = "card")
  private Card discarded;
}
