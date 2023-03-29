package com.necleo.codemonkey.lib.types.figma.properties.vector.vertex;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Jacksonized
// todo: check usage
public class Vertex {
  private double x;
  private double y;
  private String strokeCap;
  private String strokeJoin;
  private double cornerRadius;
  private String handleMirroring;
}
