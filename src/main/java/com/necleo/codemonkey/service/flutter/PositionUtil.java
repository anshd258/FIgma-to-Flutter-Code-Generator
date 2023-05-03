package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.lib.types.FigmaNode;

public class PositionUtil {

    public String getPosition(String genCode, FigmaNode figmaNode) {
        final String upperPosition = "  Positioned(child:\n";
        final String lowerPosition = "),\n";
        String top = "top:" + figmaNode.getY() + ",\n";
        String left = "left:" + figmaNode.getX() + ",\n";
        return upperPosition + genCode + top + left + lowerPosition;
    }
}
