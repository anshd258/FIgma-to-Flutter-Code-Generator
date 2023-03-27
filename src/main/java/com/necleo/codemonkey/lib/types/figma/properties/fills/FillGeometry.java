package com.necleo.codemonkey.lib.types.figma.properties.fills;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FillGeometry {
  public String windingRule; // actually an enum
  public String data;
}
