package com.rachvik.rummy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType("game_config")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Config {
  private int minNumberOfPlayers;
  private int maxNumberOfPlayers;
  private int maxTimeoutMillisToStart;
}
