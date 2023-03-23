package com.necleo.codemonkey.lib.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbsoluteTransform {
  private int[][] absoluteTransform;
}
