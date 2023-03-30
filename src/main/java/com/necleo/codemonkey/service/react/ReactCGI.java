package com.necleo.codemonkey.service.react;

import com.necleo.codemonkey.enums.FigmaNodeType;
import com.necleo.codemonkey.lib.types.FigmaNode;

public interface ReactCGI {
  String generate(FigmaNode fNode);

  FigmaNodeType getFigmaNodeType();
}
