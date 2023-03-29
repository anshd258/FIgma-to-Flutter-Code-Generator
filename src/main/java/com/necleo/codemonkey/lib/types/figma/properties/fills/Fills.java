package com.necleo.codemonkey.lib.types.figma.properties.fills;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Fills {
  public String type;
  public boolean visible;
  public float opacity;
  public String BlendMode;
  public Color color;
}
