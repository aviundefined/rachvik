package com.rachvik.rummy.converters;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.data.UdtValue;
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

    var udtValue = userDefinedType.newValue();
    udtValue = udtValue.setString("state", source.getState().name());
    udtValue = udtValue.setInt("number_of_turns_played", source.getNumberOfTurnsPlayed());
    udtValue = udtValue.setLong("last_move_id", source.getLastMoveId());
    udtValue = udtValue.setInt("active_player_index", source.getActivePlayerIndex());
    udtValue = udtValue.setList("player", source.getPlayer(), Player.class);
    udtValue = udtValue.set("joker", source.getJoker(), Card.class);
    udtValue = udtValue.setList("available", source.getAvailable(), Card.class);
    udtValue = udtValue.setList("user_hand", source.getUserHand(), UserHand.class);
    udtValue = udtValue.setList("discarded_pile", source.getAvailable(), Card.class);
    udtValue = udtValue.set("last_discarded_card", source.getLastDiscardedCard(), Card.class);
    return udtValue;
  }
}
