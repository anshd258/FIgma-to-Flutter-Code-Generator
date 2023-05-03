package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.lib.types.figma.FigmaFrameNode;

public class SpacingUtil {
  public String getSpacing(FigmaFrameNode figmaNode) {
    if (figmaNode.getLayoutMode().name().equals("HORIZONTAL")) {
      return "SizedBox(width:" + figmaNode.getItemSpacing() + ",),";
    } else {
      return "SizedBox(height:" + figmaNode.getItemSpacing() + ",),";
    }
  }
}
