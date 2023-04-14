package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.factory.IFactory;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.TadDataType;
import java.util.Map;

public interface TagFlutterCGI extends IFactory<TadDataType> {
  String generate(FigmaNode fNode, Map<String, TagData> tagData);
}
