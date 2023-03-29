package com.necleo.codemonkey.lib.types.figma;

import com.necleo.codemonkey.lib.types.FigmaNode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
@Jacksonized
public class FigmaStarNode extends FigmaNode {}
