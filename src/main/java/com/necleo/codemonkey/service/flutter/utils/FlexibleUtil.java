package com.necleo.codemonkey.service.flutter.utils;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.CounterAxisSizingMode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.LayoutAlign;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.LayoutMode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.PrimaryAxisSizingMode;

public class FlexibleUtil {
  public String getFlexible(
      String genCode,
      int hugval,
      int fillval,
      FigmaNode figmaNode,
      int Size,
      LayoutMode layoutMode) {
    int length = figmaNode.getChild().size();
    if (hugval != length
        && fillval != length
        && figmaNode.getLayoutAlign().equals(LayoutAlign.INHERIT)
        && layoutMode.equals(LayoutMode.HORIZONTAL)
        && figmaNode.getCounterAxisSizingMode().equals(CounterAxisSizingMode.FIXED)) {
      return genCode;
    } else if (hugval != length
        && fillval != length
        && figmaNode.getLayoutGrow() == 0
        && layoutMode.equals(LayoutMode.VERTICAL)
        && figmaNode.getPrimaryAxisSizingMode().equals(PrimaryAxisSizingMode.FIXED)) {
      return genCode;
    } else if ((hugval == length || fillval == length)
        && figmaNode.getLayoutAlign().equals(LayoutAlign.INHERIT)
        && layoutMode.equals(LayoutMode.HORIZONTAL)
        && figmaNode.getCounterAxisSizingMode().equals(CounterAxisSizingMode.AUTO)) {
      String upperFlexible =
          "Flexible(\n"
              + "                      flex: 1,\n"
              + "                      fit: FlexFit.tight,\n"
              + "                      child:";
      String lowerFlexible = "),\n";
      return upperFlexible + genCode + lowerFlexible;

    } else if ((hugval == length || fillval == length)
        && figmaNode.getLayoutGrow() == 0
        && layoutMode.equals(LayoutMode.VERTICAL)
        && figmaNode.getPrimaryAxisSizingMode().equals(PrimaryAxisSizingMode.AUTO)) {
      String upperFlexible =
          "Flexible(\n"
              + "                      flex: 1,\n"
              + "                      fit: FlexFit.tight,\n"
              + "                      child:";
      String lowerFlexible = "),\n";
      return upperFlexible + genCode + lowerFlexible;

    } else {
      String upperFlexible =
          "Flexible(\n"
              + "                      flex: 1,\n"
              + "                      fit: FlexFit.loose,\n"
              + "                      child:";
      String lowerFlexible = "),\n";
      return upperFlexible + genCode + lowerFlexible;
    }
  }
}
