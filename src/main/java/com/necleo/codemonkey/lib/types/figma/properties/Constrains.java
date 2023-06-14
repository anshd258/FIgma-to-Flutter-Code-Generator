package com.necleo.codemonkey.lib.types.figma.properties;

import com.necleo.codemonkey.lib.types.enums.ConstrainsValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
@Jacksonized
public class Constrains {
  ConstrainsValue horizontal;
  ConstrainsValue vertical;
}
