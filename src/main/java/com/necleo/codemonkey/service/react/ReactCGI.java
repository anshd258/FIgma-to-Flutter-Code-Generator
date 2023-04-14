package com.necleo.codemonkey.service.react;

import com.necleo.codemonkey.factory.Factory;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;

public interface ReactCGI extends Factory<FigmaNodeTypes> {
  String generate(FigmaNode fNode);
}

// public interface ReactCGI {
//  String generate(FigmaNode fNode);
//
//  FigmaNodeType getFigmaNodeType();
// }
