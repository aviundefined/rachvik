package com.rachvik.rummy.entity;

import com.rachvik.games.cards.models.CardValue;
import com.rachvik.games.cards.models.Suit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType("card")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {
  @CassandraType(type = Name.INT)
  private int deckIdentifier;

  @CassandraType(type = Name.TEXT)
  private Suit suit;

  @CassandraType(type = Name.TEXT)
  private CardValue cardValue;
}
