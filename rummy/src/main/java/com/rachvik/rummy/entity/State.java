package com.rachvik.rummy.entity;

import com.rachvik.games.cards.models.GameState;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType("game_state")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class State {

  @Column("state")
  private GameState state;

  @Column("available")
  private List<Card> available;

  @Column("joker")
  private Card joker;

  @Column("player")
  private List<Player> player;

  @Column("user_hand")
  private List<UserHand> userHand;

  @Column("discarded_pile")
  private List<Card> discardedPile;

  @Column("number_of_turns_played")
  private int numberOfTurnsPlayed;

  @Column("last_move_id")
  private long lastMoveId;

  @Column("last_discarded_card")
  private Card lastDiscardedCard;

  @Column("active_player_index")
  private int activePlayerIndex;
}
