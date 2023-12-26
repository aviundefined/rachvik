package com.rachvik.common.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtils {

  public boolean isEmpty(final String s) {
    return org.apache.commons.lang3.StringUtils.isEmpty(s);
  }

  public boolean isNonEmpty(final String s) {
    return org.apache.commons.lang3.StringUtils.isNoneEmpty(s);
  }

  public boolean isBlank(final String s) {
    return org.apache.commons.lang3.StringUtils.isBlank(s);
  }
}
