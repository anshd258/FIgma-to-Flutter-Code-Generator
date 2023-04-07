package com.necleo.codemonkey.lib.types.figma.properties.fills.subtypes;

import com.necleo.codemonkey.lib.types.figma.properties.fills.Color;
import com.necleo.codemonkey.lib.types.figma.properties.fills.Fills;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
@Jacksonized
public class FillsSolid extends Fills {
  Color color;
}
