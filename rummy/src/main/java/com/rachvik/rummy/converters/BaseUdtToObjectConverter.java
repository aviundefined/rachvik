package com.rachvik.rummy.converters;

import com.datastax.oss.driver.api.core.data.UdtValue;
import lombok.NoArgsConstructor;
import org.springframework.core.convert.converter.Converter;

@NoArgsConstructor
public abstract class BaseUdtToObjectConverter<S> implements Converter<UdtValue, S> {}
