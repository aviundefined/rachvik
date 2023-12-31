package com.rachvik.rummy.converters;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.data.UdtValue;
import org.springframework.core.convert.converter.Converter;

public abstract class BaseObjectToUdtConverter<S> implements Converter<S, UdtValue> {
  protected final CqlSession session;
  protected final String keyspaceName;

  protected BaseObjectToUdtConverter(final CqlSession session, final String keyspaceName) {
    this.session = session;
    this.keyspaceName = keyspaceName;
  }

}
