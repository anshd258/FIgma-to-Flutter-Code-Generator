package com.necleo.codemonkey.factory.mapper;

import com.necleo.codemonkey.factory.Factory;
import java.util.Map;

public class EnumMapper<E extends Factory<T>, T extends Enum<T>> implements FactoryMapper<E, T> {

  @Override
  public void map(E bean, Map<T, E> map) {
    T strategy = bean.getStrategy();
    if (map.containsKey(strategy)) {
      throw new IllegalStateException(
          "Multiple implementations found for Type: "
              + strategy
              + " and class: "
              + bean.getClass().getName());
    }
    map.put(strategy, bean);
  }
}
