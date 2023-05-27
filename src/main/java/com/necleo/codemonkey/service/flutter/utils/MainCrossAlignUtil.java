package com.necleo.codemonkey.service.flutter.utils;

import com.necleo.codemonkey.lib.types.enums.figmaEnums.CounterAxisAlignItems;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.PrimaryAxisAlignItems;

public class MainCrossAlignUtil {
  public String getMainAxisAlignment(PrimaryAxisAlignItems primaryAxisAlignItems) {

    String mainAlignType =
        switch (primaryAxisAlignItems) {
          case MIN -> "start";
          case CENTER -> "center";
          case MAX -> "end";
          case SPACE_BETWEEN -> "spaceBetween";
          default -> "";
        };
    if (mainAlignType.equals("")) {
      return "";
    }
    return "\n mainAxisAlignment: MainAxisAlignment." + mainAlignType + ",";
  }

  public String getCrossAxisAlignment(CounterAxisAlignItems counterAxisAlignItems) {

    String mainAlignType =
        switch (counterAxisAlignItems) {
          case MIN -> "start";
          case CENTER -> "center";
          case MAX -> "end";
          default -> "";
        };
    if (mainAlignType.equals("")) {
      return "";
    }
    return "\n crossAxisAlignment: CrossAxisAlignment." + mainAlignType + ",";
  }
}
