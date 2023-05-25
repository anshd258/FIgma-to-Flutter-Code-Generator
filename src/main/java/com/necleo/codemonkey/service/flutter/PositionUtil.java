package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.lib.types.FigmaNode;

public class PositionUtil {

  public String getPosition(String genCode, FigmaNode figmaNode) {
    final String upperPosition = "  Positioned(";
    final String lowerPosition = "),\n";
    String top = "top:" + figmaNode.getY() + ",\n";
    String left = "left:" + figmaNode.getX() + ",\n";
    String child = "child:" + genCode + "\n";
    return upperPosition + top + left + child  + lowerPosition;
  }
}
