package com.necleo.codemonkey.model.factory;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.TagDataType;
import java.util.Map;
import java.util.Optional;

public record FigmaNodeMapper(FigmaNodeTypes figmaNodeTypes, TagDataType tagDataType) {

  public static FigmaNodeMapper of(FigmaNode fNode, Map<String, TagData> tagDataMap) {
    TagDataType tagDataType =
        Optional.ofNullable(tagDataMap.get(fNode.getId()))
            .map(TagData::getTagName)
            .map(String::toUpperCase)
            .map(TagDataType::valueOf)
            .orElse(null);
    return new FigmaNodeMapper(fNode.getType(), tagDataType);
  }
}
