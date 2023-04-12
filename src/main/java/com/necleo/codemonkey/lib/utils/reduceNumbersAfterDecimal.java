package com.necleo.codemonkey.lib.utils;

public class reduceNumbersAfterDecimal {
  public String reducerDecimal(float num, int to) {
    return String.valueOf((Math.round(num * 10.0) / 10.0));
  }
}
