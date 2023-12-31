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

@Table("game")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game {
  @PrimaryKey
  @Column("game_id")
  private String gameId;

  @CassandraType(type = Name.UDT, userTypeName = "game_config")
  @Column("game_config")
  private Config config;

  @CassandraType(type = Name.UDT, userTypeName = "game_state")
  @Column("game_state")
  private State state;
}
