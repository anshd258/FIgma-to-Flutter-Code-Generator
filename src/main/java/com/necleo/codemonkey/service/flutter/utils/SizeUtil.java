package com.necleo.codemonkey.service.flutter.utils;

import com.necleo.codemonkey.flutter.index.FlutterGI;
import com.necleo.codemonkey.lib.types.FigmaNode;

public class SizeUtil {
  public String getHeight(FigmaNode fNode, FigmaNode mainScreen, FlutterGI flutterGI) {
    if (fNode.getConstraints() != null) {
      switch (fNode.getConstraints().getVertical()) {
        case STRETCH, SCALE -> {
          flutterGI.setResponsive(true);
          return "height:" + (((float) fNode.getHeight() / mainScreen.getHeight()) * 100) + ".h,\n";
        }
        default -> {
          return "height:" + Integer.toString(fNode.getHeight()) + ",\n";
        }
      }

    } else {
      return "height:" + Integer.toString(fNode.getHeight()) + ",\n";
    }
  }

  public String getWidth(FigmaNode fNode, FigmaNode mainScreen, FlutterGI flutterGI) {
    if (fNode.getConstraints() != null) {
      switch (fNode.getConstraints().getHorizontal()) {
        case STRETCH, SCALE -> {
          flutterGI.setResponsive(true);
          return "width:" + (((float)  fNode.getWidth() / mainScreen.getWidth()) * 100) + ".w,\n";
        }
        default -> {
          return "width:" + Integer.toString(fNode.getWidth()) + ",\n";
        }
      }
    } else {
      return "width:" + Integer.toString(fNode.getWidth()) + ",\n";
    }
  }
}
