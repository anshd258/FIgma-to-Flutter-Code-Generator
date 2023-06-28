package com.necleo.codemonkey.service.flutter.utils;

import com.necleo.codemonkey.flutter.index.FlutterGI;
import com.necleo.codemonkey.lib.types.FigmaNode;

public class SizeUtil {
  public String getHeight(FigmaNode fNode, FigmaNode mainScreen, FlutterGI flutterGI) {
    if (fNode.getConstraints() != null) {
      switch (fNode.getConstraints().getVertical()) {
        case STRETCH, SCALE -> {
          flutterGI.setResponsive(true);
          return "height:" + (fNode.getHeight() / (mainScreen.getHeight() * 100f)) + ".h,\n";
        }
        default -> {
          return "height:" + fNode.getHeight() + ",\n";
        }
      }

    } else {
      return "height:" + fNode.getHeight() + ",\n";
    }
  }

  public String getWidth(FigmaNode fNode, FigmaNode mainScreen, FlutterGI flutterGI) {
    if (fNode.getConstraints() != null) {
      switch (fNode.getConstraints().getHorizontal()) {
        case STRETCH, SCALE -> {
          flutterGI.setResponsive(true);
          return "width:" + (fNode.getWidth() / (mainScreen.getWidth() * 100f)) + ".w,\n";
        }
        default -> {
          return "width:" + fNode.getWidth() + ",\n";
        }
      }
    } else {
      return "width:" + fNode.getWidth() + ",\n";
    }
  }
}
