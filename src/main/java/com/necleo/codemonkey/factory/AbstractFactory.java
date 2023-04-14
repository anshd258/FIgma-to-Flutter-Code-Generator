package com.necleo.codemonkey.factory;

import com.necleo.codemonkey.factory.domain.FactoryMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class AbstractFactory<T, E extends Factory<?>> {
  Map<T, E> factoryMap;

  protected AbstractFactory(List<E> beans, FactoryMapper<E, T> mapper) {
    factoryMap = new HashMap<>();
    beans.forEach(bean -> mapper.map(bean, factoryMap));
  }

  public Optional<E> getProcessor(T type) {
    return Optional.ofNullable(factoryMap.get(type));
  }
}
