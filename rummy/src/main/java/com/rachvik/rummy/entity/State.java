package com.rachvik.rummy.entity;

import com.rachvik.games.cards.models.GameState;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class State {
  private GameState state;
  private List<Card> available;
  private Card joker;
  private List<Player> player;
  private List<UserHand> userHand;
  private List<Card> discardedPile;
  private int numberOfTurnsPlayed;
  private long lastMoveId;
  private Card lastDiscardedCard;
  private int activePlayerIndex;
}
