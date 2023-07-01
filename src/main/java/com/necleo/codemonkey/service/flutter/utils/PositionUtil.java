package com.necleo.codemonkey.service.flutter.utils;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.model.factory.FlutterWI;
import org.springframework.stereotype.Service;

public class PositionUtil {
  static PercentageUtil percentageUtil;

  public static String getPosition(
      String genCode, FigmaNode figmaNode, FigmaNode ParentNode, FlutterWI FlutterWI) {
    final String upperPosition = "  Positioned(";
    final String lowerPosition = "),\n";
    String constrain1 = "";
    String constrain2 = "";
    if (figmaNode.getConstraints() != null) {
      switch (figmaNode.getConstraints().getHorizontal()) {
        case MAX -> constrain1 =
            "right" + (ParentNode.getWidth() - (figmaNode.getWidth() + figmaNode.getX()));
        case MIN -> constrain1 = "left:" + figmaNode.getX() + ",\n";
        case SCALE -> constrain1 =
            "left:"
                + percentageUtil.getLeft(figmaNode.getX(), FlutterWI.getMainScreen().getWidth())
                + ".w,\n";
        default -> constrain1 = "";
      }
      switch (figmaNode.getConstraints().getVertical()) {
        case MAX -> constrain2 =
            "bottom" + (ParentNode.getHeight() - (figmaNode.getHeight() + figmaNode.getY()));
        case MIN -> constrain2 = "top:" + figmaNode.getY() + ",\n";
        case SCALE -> constrain2 =
            "top:"
                + percentageUtil.getTop(figmaNode.getY(), FlutterWI.getMainScreen().getHeight())
                + ".h,\n";
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
