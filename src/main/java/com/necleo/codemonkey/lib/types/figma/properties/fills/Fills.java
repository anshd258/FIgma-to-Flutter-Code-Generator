package com.necleo.codemonkey.lib.types.figma.properties.fills;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Jacksonized
public class Fills {
  public String type;
  public boolean visible;
  public float opacity;
  public String BlendMode;
  public Color color;
}
