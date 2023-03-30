package com.necleo.codemonkey.service.react;

import com.necleo.codemonkey.enums.FigmaNodeType;

public interface ReactCGI<FigmaNodeTypeObject> {
  String generate(FigmaNodeTypeObject fNode);

  FigmaNodeType getFigmaNodeType();
}
