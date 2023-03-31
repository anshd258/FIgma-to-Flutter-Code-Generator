package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.factory.IFactory;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;

public interface FlutterCGI extends IFactory<FigmaNodeTypes> {
  String generate(FigmaNode fNode);
}
