package com.necleo.codemonkey.factory.domain;

import com.necleo.codemonkey.factory.Factory;
import java.util.Map;
import java.util.Set;

public class SetMapper<E extends Factory<Set<T>>, T> implements FactoryMapper<E, T> {

  @Override
  public void map(E bean, Map<T, E> map) {
    Set<T> strategies = bean.getStrategy();
    strategies.forEach(
        strategy -> {
          if (map.containsKey(strategy)) {
            throw new IllegalStateException(
                "Multiple implementations found for Type: "
                    + strategy
                    + " and class: "
                    + bean.getClass().getName());
          }
          map.put(strategy, bean);
        });
  }
}
