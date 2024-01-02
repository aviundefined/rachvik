package com.rachvik.rummy.converters;

import com.datastax.oss.driver.api.core.data.UdtValue;
import com.rachvik.games.cards.models.CardValue;
import com.rachvik.games.cards.models.Suit;
import com.rachvik.rummy.entity.Card;
import com.rachvik.rummy.entity.Deck;
import com.rachvik.rummy.entity.Player;
import java.util.ArrayList;
import lombok.NoArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class UdtValueToPlayerConverter extends BaseUdtToObjectConverter<Player> {

  @Override
  public Player convert(UdtValue source) {

    return Player.builder().username(source.getString("username")).build();
  }
}
