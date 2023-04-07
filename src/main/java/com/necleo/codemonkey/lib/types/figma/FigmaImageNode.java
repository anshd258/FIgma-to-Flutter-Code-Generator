package com.necleo.codemonkey.lib.types.figma;

import com.necleo.codemonkey.lib.types.enums.figmaEnums.LayoutAlign;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.LayoutPositioning;

public class FigmaImageNode extends FigmaRectangleNode {
  boolean constrainProportions;
  LayoutAlign layoutAlign;
  int layoutGrow;
  LayoutPositioning layoutPositioning;
}
