package com.necleo.codemonkey.factory.mapper;

import com.necleo.codemonkey.factory.Factory;
import java.util.Map;

@FunctionalInterface
public interface FactoryMapper<E extends Factory<?>, T> {
  void map(E bean, Map<T, E> map);
}
