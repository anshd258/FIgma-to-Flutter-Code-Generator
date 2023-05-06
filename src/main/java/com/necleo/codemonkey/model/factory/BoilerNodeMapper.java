package com.necleo.codemonkey.model.factory;

import com.necleo.codemonkey.lib.types.enums.boilerplate.BoilerType;

public record BoilerNodeMapper(BoilerType boilerType, String args) {

  public static BoilerNodeMapper of(BoilerType boilerType, String args) {

    return new BoilerNodeMapper(boilerType, args);
  }

  public static BoilerNodeMapper of(BoilerType boilerType) {

    return new BoilerNodeMapper(boilerType, null);
  }
}
