package com.necleo.codemonkey.service.flutter.utils;

import com.necleo.codemonkey.lib.types.FigmaNode;

public class PositionUtil {

  public String getPosition(String genCode, FigmaNode figmaNode, FigmaNode ParentNode) {
    final String upperPosition = "  Positioned(";
    final String lowerPosition = "),\n";
    String constrain1 = "";
    String constrain2 = "";
    if (figmaNode.getConstraints() != null) {
      switch (figmaNode.getConstraints().getHorizontal()) {
        case MAX -> constrain1 =
            "right" + (ParentNode.getWidth() - (figmaNode.getWidth() + figmaNode.getX()));
        case MIN -> constrain1 = "left:" + figmaNode.getX() + ",\n";
        default -> constrain1 = "";
      }
      switch (figmaNode.getConstraints().getVertical()) {
        case MAX -> constrain2 =
            "bottom" + (ParentNode.getHeight() - (figmaNode.getHeight() + figmaNode.getY()));
        case MIN -> constrain2 = "top:" + figmaNode.getY() + ",\n";
        default -> constrain2 = "";
      }
    } else {
      constrain1 = "left:" + figmaNode.getX() + ",\n";
      constrain2 = "top:" + figmaNode.getY() + ",\n";
    }

    String child = "child:" + genCode + "\n";
    return upperPosition + constrain1 + constrain2 + child + lowerPosition;
  }
}
