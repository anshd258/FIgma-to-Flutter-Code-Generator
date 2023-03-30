package com.necleo.codemonkey.service.react;

import com.necleo.codemonkey.lib.types.figma.FigmaRectangleNode;

public interface ReactCGI <FigmaNodeType>{
    public String generate(FigmaNodeType fNode);
}
