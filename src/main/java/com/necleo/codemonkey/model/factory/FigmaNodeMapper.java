package com.necleo.codemonkey.model.factory;

import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.TagDataType;

public record FigmaNodeMapper(FigmaNodeTypes figmaNodeTypes, TagDataType tagDataType) {}
