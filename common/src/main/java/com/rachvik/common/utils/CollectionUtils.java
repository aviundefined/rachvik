package com.rachvik.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CollectionUtils {

  public boolean isEmpty(final Collection<?> collections) {
    return org.apache.commons.collections4.CollectionUtils.isEmpty(collections);
  }

  public boolean isNonEmpty(final Collection<?> collections) {
    return org.apache.commons.collections4.CollectionUtils.isNotEmpty(collections);
  }

  public <T> List<T> emptyIfNull(final List<T> list) {
    if (list == null) {
      return new ArrayList<>();
    }
    return list;
  }

  public <T> Set<T> emptyIfNull(final Set<T> set) {
    if (set == null) {
      return new HashSet<>();
    }
    return set;
  }

  public <T> Collection<T> emptyIfNull(Collection<T> collection) {
    return org.apache.commons.collections4.CollectionUtils.emptyIfNull(collection);
  }
}
