package com.rachvik.rummy.converters;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.type.UserDefinedType;
import com.rachvik.rummy.entity.Card;
import com.rachvik.rummy.entity.Player;
import com.rachvik.rummy.entity.State;
import com.rachvik.rummy.entity.UserHand;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
public class StateToUdtValueConverter extends BaseObjectToUdtConverter<State> {

  public StateToUdtValueConverter(final CqlSession session, final String keyspaceName) {
    super(session, keyspaceName);
  }

  @Override
  public UdtValue convert(State source) {
    val metadata = session.getMetadata();
    val userDefinedType =
        metadata
            .getKeyspace(keyspaceName) // Replace with your keyspace name
            .flatMap(ks -> ks.getUserDefinedType("game_state"))
            .orElseThrow(() -> new IllegalStateException("User-defined type 'card' not found"));

    return userDefinedType
        .newValue()
        .setString("state", source.getState().name())
        .setList("available", source.getAvailable(), Card.class)
        .set("joker", source.getJoker(), Card.class)
        .setList("player", source.getPlayer(), Player.class)
        .setList("user_hand", source.getUserHand(), UserHand.class)
        .setList("discarded_pile", source.getAvailable(), Card.class)
        .setInt("number_of_turns_played", source.getNumberOfTurnsPlayed())
        .setLong("last_move_id", source.getLastMoveId())
        .set("last_discarded_card", source.getLastDiscardedCard(), Card.class)
        .setInt("active_player_index", source.getActivePlayerIndex());
  }
}
