package com.necleo.codemonkey.lib.types.figma.properties.fills.subtypes;

import com.necleo.codemonkey.lib.types.figma.properties.fills.Fills;
import com.necleo.codemonkey.lib.types.figma.properties.fills.GradientStops;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
@Jacksonized
public class FillsGradient extends Fills {
  String visible;
  float opacity;
  String blendMode;
  List<GradientStops> gradientStops;
}
