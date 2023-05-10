package com.necleo.codemonkey.lib.types.figma.properties.effects;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Jacksonized
public class EffectColor {
        double r;
        double g;
        double b;
        double a;
}
