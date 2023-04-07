package com.necleo.codemonkey.lib.types.figma.properties.fills.subtypes;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Filters {
  int exposure;
  int contrast;
  int saturation;
  int temperature;
  int tint;
  int highlights;
  int shadows;
}
