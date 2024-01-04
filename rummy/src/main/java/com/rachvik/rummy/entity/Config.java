package com.rachvik.rummy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType("game_config")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Config {
  @Column("min_number_of_players")
  private int minNumberOfPlayers;
  @Column("max_number_of_players")
  private int maxNumberOfPlayers;
  @Column("max_timeout_millis_to_start")
  private int maxTimeoutMillisToStart;
}
