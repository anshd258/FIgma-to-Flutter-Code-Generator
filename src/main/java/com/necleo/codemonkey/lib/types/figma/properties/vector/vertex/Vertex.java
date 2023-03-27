package com.necleo.codemonkey.lib.types.figma.properties.vector.vertex;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Vertex {
  private double x;
  private double y;
  private String strokeCap;
  private String strokeJoin;
  private double cornerRadius;
  private String handleMirroring;
}
