package com.necleo.codemonkey.lib.types.figma.properties.strokes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Strokes {
  public String type; // enum
  public boolean visible;
  public int opacity;
  public String BlendMode;
  public Color color;
}
