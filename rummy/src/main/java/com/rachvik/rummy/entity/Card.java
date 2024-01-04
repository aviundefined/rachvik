package com.rachvik.rummy.entity;

import com.rachvik.games.cards.models.CardValue;
import com.rachvik.games.cards.models.Suit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType(value = "card")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {
  @Column("deck_identifier")
  private int deckIdentifier;

  @Column("suit")
  private Suit suit;

  @Column("card_value")
  private CardValue cardValue;
}
