package com.necleo.codemonkey.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class Factory<T , E extends IFactory<T>> {
  Map<T, E> factoryMap;

  public Factory(List<E> beans) {
    factoryMap = new HashMap<>();

    beans.forEach(this::loadBeanToFactoryMap);
  }

  public Optional<E> getProcessor(T type) {
    return Optional.ofNullable(factoryMap.get(type));
  }

  protected void loadBeanToFactoryMap(E bean) {

    T type = bean.getEnumMapping();

    if (factoryMap.containsKey(type)) {
      throw new IllegalStateException(
          "Multiple implementations found for Type: "
              + type
              + "and class: "
              + bean.getClass().getName());
    }
    factoryMap.put(type, bean);
  }
}
