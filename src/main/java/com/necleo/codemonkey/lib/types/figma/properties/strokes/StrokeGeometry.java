package com.necleo.codemonkey.lib.types.figma.properties.strokes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StrokeGeometry {
  public String windingRule; // enum
  public String data;
}
