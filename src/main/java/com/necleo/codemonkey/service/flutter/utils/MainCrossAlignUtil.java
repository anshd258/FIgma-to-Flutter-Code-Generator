package com.necleo.codemonkey.service.flutter.utils;

import com.necleo.codemonkey.lib.types.enums.figmaEnums.CounterAxisAlignItems;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.PrimaryAxisAlignItems;
import org.springframework.stereotype.Service;

public class MainCrossAlignUtil {
  public static String getMainAxisAlignment(PrimaryAxisAlignItems primaryAxisAlignItems) {

    return switch (primaryAxisAlignItems) {
      case MIN -> "start";
      case CENTER -> "center";
      case MAX -> "end";
      case SPACE_BETWEEN -> "spaceBetween";
    };
  }

  public static String getCrossAxisAlignment(CounterAxisAlignItems counterAxisAlignItems) {
    return switch (counterAxisAlignItems) {
      case MIN -> "start";
      case CENTER -> "center";
      case MAX -> "end";
      case BASELINE -> "baseline";
    };
  }

  public String getWrapAlignment(PrimaryAxisAlignItems primaryAxisAlignItems) {
    String alignType;

    alignType =
        switch (primaryAxisAlignItems) {
          case MIN -> "start";
          case CENTER -> "center";
          case MAX -> "end";
          case SPACE_BETWEEN -> "spaceBetween";
        };

    return "\n alignment: WrapAlignment." + alignType + ",";
  }

  public String getRunAlignment(CounterAxisAlignItems counterAxisAlignItems) {
    String alignType;

    alignType =
        switch (counterAxisAlignItems) {
          case MIN -> "start";
          case CENTER -> "center";
          case MAX -> "end";
          default -> "";
        };

    if (alignType.equals("")) {
      return "";
    }
    return "\n runAlignment: WrapAlignment." + alignType + ",";
  }

  public String getAlignmentRow(
      PrimaryAxisAlignItems primaryAxisAlignItems, CounterAxisAlignItems counterAxisAlignItems) {
    String alignType = "";

    alignType =
        switch (primaryAxisAlignItems) {
          case MIN -> switch (counterAxisAlignItems) {
            case MIN -> "topLeft";
            case CENTER -> "centerLeft";
            case MAX -> "bottomLeft";
            default -> "";
          };
          case CENTER -> switch (counterAxisAlignItems) {
            case MIN -> "topCenter";
            case CENTER -> "center";
            case MAX -> "bottomCenter";
            default -> "";
          };
          case MAX -> switch (counterAxisAlignItems) {
            case MIN -> "topRight";
            case CENTER -> "centerRight";
            case MAX -> "bottomRight";
            default -> "";
          };
          default -> "";
        };

    if (alignType.equals("")) {
      return "";
    }
    return "\n alignment: Alignment." + alignType + ",";
  }

  public String getAlignmentColumn(
      PrimaryAxisAlignItems primaryAxisAlignItems, CounterAxisAlignItems counterAxisAlignItems) {
    String alignType;

    alignType =
        switch (primaryAxisAlignItems) {
          case MIN -> switch (counterAxisAlignItems) {
            case MIN -> "topLeft";
            case CENTER -> "topCenter";
            case MAX -> "topRight";
            default -> "";
          };
          case CENTER -> switch (counterAxisAlignItems) {
            case MIN -> "centerLeft";
            case CENTER -> "center";
            case MAX -> "centerRight";
            default -> "";
          };
          case MAX -> switch (counterAxisAlignItems) {
            case MIN -> "bottomLeft";
            case CENTER -> "bottomCenter";
            case MAX -> "bottomRight";
            default -> "";
          };
          default -> "";
        };

    if (alignType.equals("")) {
      return "";
    }
    return "\n alignment: Alignment." + alignType + ",";
  }
}
