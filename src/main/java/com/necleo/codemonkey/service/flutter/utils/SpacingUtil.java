package com.necleo.codemonkey.service.flutter.utils;

import com.necleo.codemonkey.lib.types.figma.FigmaFrameNode;

public class SpacingUtil {
  public static String getSpacing(FigmaFrameNode figmaNode) {
    if (figmaNode.getLayoutMode().name().equals("HORIZONTAL")) {
      return "\nSizedBox( width: " + figmaNode.getItemSpacing() + ",),";
    } else {
      return "\nSizedBox( height: " + figmaNode.getItemSpacing() + ",),";
    }
  }
}
