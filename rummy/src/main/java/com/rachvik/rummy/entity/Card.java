package com.rachvik.rummy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType("card")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {
  private String suit;

  private String cardValue;
}
