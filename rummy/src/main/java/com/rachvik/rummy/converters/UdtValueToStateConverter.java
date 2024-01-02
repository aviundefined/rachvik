package com.rachvik.rummy.converters;

import com.datastax.oss.driver.api.core.data.UdtValue;
import com.rachvik.games.cards.models.GameState;
import com.rachvik.rummy.entity.Card;
import com.rachvik.rummy.entity.Player;
import com.rachvik.rummy.entity.State;
import com.rachvik.rummy.entity.UserHand;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class UdtValueToStateConverter extends BaseUdtToObjectConverter<State> {

  @Override
  public State convert(UdtValue source) {
    return State.builder()
        .state(GameState.valueOf(source.getString("state")))
        .available(source.getList("available", Card.class))
        .joker(source.get("joker", Card.class))
        .player(source.getList("player", Player.class))
        .userHand(source.getList("user_hand", UserHand.class))
        .discardedPile(source.getList("discarded_pile", Card.class))
        .numberOfTurnsPlayed(source.getInt("number_of_turns_played"))
        .lastMoveId(source.getLong("last_move_id"))
        .lastDiscardedCard(source.get("last_discarded_card", Card.class))
        .activePlayerIndex(source.getInt("active_player_index"))
        .build();
  }
}
