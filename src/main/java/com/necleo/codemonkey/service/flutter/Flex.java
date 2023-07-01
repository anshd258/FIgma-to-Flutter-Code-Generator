package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.lib.types.enums.figmaEnums.LayoutMode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.LayoutWrap;
import com.necleo.codemonkey.lib.types.figma.FigmaFrameNode;
import com.necleo.codemonkey.service.flutter.utils.MainCrossAlignUtil;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

@Builder(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PROTECTED)
public class Flex {

  enum Type {
    Row,
    Column,
    Wrap,
    Stack
  }

  Flex.Type type;

  public String wrap(String child, FigmaFrameNode figmaNode) {
    String properties = getProperties(figmaNode);
    return """
                %s(
                  %s
                  %s
                ),
                """
        .formatted(type.name(), properties, child);
  }

  private String getProperties(FigmaFrameNode figmaNode) {
    if (!figmaNode.getLayoutMode().equals(LayoutMode.NONE)) {
      if (figmaNode.getLayoutWrap().equals(LayoutWrap.WRAP)) {
        return """
                  direction: Axis.horizontal,
                  spacing: %s,
                  runSpacing: %s,
                 """
            .formatted(figmaNode.getItemSpacing(), figmaNode.getCounterAxisSpacing());
      } else {
        String mainAxis = MainCrossAlignUtil.getMainAxisAlignment(figmaNode.getPrimaryAxisAlignItems());
        String crossAxis = MainCrossAlignUtil.getCrossAxisAlignment(figmaNode.getCounterAxisAlignItems());
        return """
                  mainAxisAlignment: MainAxisAlignment.%s,
                  crossAxisAlignment: CrossAxisAlignment.%s,
                 """
            .formatted(
                mainAxis,
                crossAxis);
      }
    } else {
      return """
                 fit: StackFit.expand,
                 alignment: Alignment.center,
                 """;
    }
  }

  static Flex of(Flex.Type type) {
    return Flex.builder().type(type).build();
  }
}
