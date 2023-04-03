package com.necleo.codemonkey.factory;

public interface IFactory<T extends Enum<T>> {
  T getEnumMapping();
}
