package com.necleo.codemonkey.service.react;

import com.necleo.codemonkey.factory.IFactory;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;

public interface ReactTaggedDataCGI extends ReactCGI {
    String generate(FigmaNode fNode, TagData tagData);
}

// public interface ReactCGI {
//  String generate(FigmaNode fNode);
//
//  FigmaNodeType getFigmaNodeType();
// }
