package com.rachvik.rummy.entity;

import com.rachvik.games.cards.models.GameState;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType("game_state")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class State {

  @CassandraType(type = Name.TEXT)
  private GameState state;

  @CassandraType(type = Name.UDT)
  private List<Card> available;

  @CassandraType(type = Name.UDT, userTypeName = "card")
  private Card joker;

  @CassandraType(type = Name.UDT, userTypeName = "player")
  private List<Player> player;

  @CassandraType(type = Name.UDT)
  private List<UserHand> userHand;

  @CassandraType(type = Name.UDT)
  private List<Card> discardedPile;

  @CassandraType(type = Name.UDT)
  private List<Card> closedPiles;

  private int numberOfTurnsPlayed;
  private long lastMoveId;

  @CassandraType(type = Name.UDT, userTypeName = "card")
  private Card lastDiscardedCard;

  private int activePlayerIndex;
}
