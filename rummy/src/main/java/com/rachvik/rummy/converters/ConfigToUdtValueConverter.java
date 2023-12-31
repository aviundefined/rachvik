package com.rachvik.rummy.converters;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.type.UserDefinedType;
import com.rachvik.rummy.entity.Config;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
public class ConfigToUdtValueConverter extends BaseObjectToUdtConverter<Config> {

  protected ConfigToUdtValueConverter(final CqlSession session, final String keyspaceName) {
    super(session, keyspaceName);
  }

  @Override
  public UdtValue convert(final Config source) {
    val metadata = session.getMetadata();
    UserDefinedType userDefinedType =
        metadata
            .getKeyspace(keyspaceName) // Replace with your keyspace name
            .flatMap(ks -> ks.getUserDefinedType("game_config"))
            .orElseThrow(
                () -> new IllegalStateException("User-defined type 'game_config' not found"));

    return userDefinedType
        .newValue()
        .setInt("min_number_of_players", source.getMinNumberOfPlayers())
        .setInt("max_number_of_players", source.getMaxNumberOfPlayers())
        .setInt("max_timeout_millis_to_start", source.getMaxTimeoutMillisToStart());
  }
}
