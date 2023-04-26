package com.necleo.codemonkey.service.react;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;

public interface ReactTaggedDataCGI extends ReactCGI {
  String generate(FigmaNode fNode, TagData tagData);
}

// public interface ReactCGI {
//  String generate(FigmaNode fNode);
//
//  FigmaNodeType getFigmaNodeType();
// }
