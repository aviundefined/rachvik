package com.rachvik.rummy.config;

import com.rachvik.rummy.converters.CardToUdtValueConverter;
import com.rachvik.rummy.converters.ConfigToUdtValueConverter;
import com.rachvik.rummy.converters.DeckToUdtValueConverter;
import com.rachvik.rummy.converters.PlayerToUdtValueConverter;
import com.rachvik.rummy.converters.StateToUdtValueConverter;
import com.rachvik.rummy.converters.UdtValueToCardConverter;
import com.rachvik.rummy.converters.UdtValueToConfigConverter;
import com.rachvik.rummy.converters.UdtValueToDeckConverter;
import com.rachvik.rummy.converters.UdtValueToPlayerConverter;
import com.rachvik.rummy.converters.UdtValueToStateConverter;
import com.rachvik.rummy.converters.UdtValueToUserHandConverter;
import com.rachvik.rummy.converters.UserHandToUdtValueConverter;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.CassandraCustomConversions;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;
import org.springframework.data.cassandra.core.mapping.UserTypeResolver;
import org.springframework.lang.NonNull;

@Configuration
@RequiredArgsConstructor
public class CassandraConfig extends AbstractCassandraConfiguration {
  @Value("${spring.cassandra.keyspace-name}")
  private String keyspaceName;

  @Override
  @NonNull
  @Bean(name = "keyspaceName")
  protected String getKeyspaceName() {
    return keyspaceName;
  }

  @Bean
  public UserTypeResolver userTypeResolver() {
    return new SimpleUserTypeResolver(getRequiredSession());
  }

  @Override
  @Bean
  @NonNull
  public CassandraConverter cassandraConverter() {
    val convertor = new MappingCassandraConverter(new CassandraMappingContext());
    val session = getRequiredSession();
    convertor.setUserTypeResolver(new SimpleUserTypeResolver(session));
    convertor.setCustomConversions(
        new CassandraCustomConversions(
            Arrays.asList(
                new CardToUdtValueConverter(session, getKeyspaceName()),
                new ConfigToUdtValueConverter(session, getKeyspaceName()),
                new DeckToUdtValueConverter(session, getKeyspaceName()),
                new PlayerToUdtValueConverter(session, getKeyspaceName()),
                new StateToUdtValueConverter(session, getKeyspaceName()),
                new UserHandToUdtValueConverter(session, getKeyspaceName()),
                new UdtValueToCardConverter(),
                new UdtValueToConfigConverter(),
                new UdtValueToDeckConverter(),
                new UdtValueToPlayerConverter(),
                new UdtValueToStateConverter(),
                new UdtValueToUserHandConverter())));
    return convertor;
  }
}
