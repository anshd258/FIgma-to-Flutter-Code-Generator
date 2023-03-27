package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.lib.types.figma.FigmaRectangleNode;

public interface  FlutterCGI <FigmaNodeType> {
    public String generate(FigmaNodeType fNode);
}
