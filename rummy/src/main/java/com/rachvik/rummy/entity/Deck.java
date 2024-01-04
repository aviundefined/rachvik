package com.rachvik.rummy.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType("deck")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Deck {

  @Column("card")
  private List<Card> card;
}
