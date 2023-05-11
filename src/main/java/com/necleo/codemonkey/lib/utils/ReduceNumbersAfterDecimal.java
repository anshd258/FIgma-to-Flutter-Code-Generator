package com.necleo.codemonkey.lib.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ReduceNumbersAfterDecimal {
  public String reducerDecimal(double num) {
    return String.valueOf((Math.round(num * 255)));
  }
}
