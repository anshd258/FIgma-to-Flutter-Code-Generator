package com.necleo.codemonkey.factory.domain;

import com.necleo.codemonkey.factory.Factory;
import java.util.Map;

public interface FactoryMapper<E extends Factory<?>, T> {
  void map(E bean, Map<T, E> map);
}
