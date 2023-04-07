package com.necleo.codemonkey.lib.types.figma.properties.fills.subtypes;

import com.necleo.codemonkey.lib.types.figma.properties.fills.Fills;
import com.necleo.codemonkey.lib.types.figma.properties.fills.enums.ScaleMode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Jacksonized
public class FillsImage extends Fills {
    ScaleMode scaleMode;
    int[][] imageTransform;
    int scalingFactor;
    int rotation;
    Filters filters;
    String imageHash;
}