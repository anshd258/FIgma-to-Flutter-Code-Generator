package com.necleo.codemonkey.lib.types.figma.properties.strokes;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Jacksonized
public class StrokeGeometry {
  public String windingRule; // todo: enum
  public String data;
}
