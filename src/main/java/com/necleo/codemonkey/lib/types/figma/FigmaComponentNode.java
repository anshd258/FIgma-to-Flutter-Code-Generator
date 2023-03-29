package com.necleo.codemonkey.lib.types.figma;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.*;
import com.necleo.codemonkey.lib.types.figma.properties.fills.FillGeometry;
import com.necleo.codemonkey.lib.types.figma.properties.fills.Fills;
import com.necleo.codemonkey.lib.types.figma.properties.strokes.Strokes;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Jacksonized
public class FigmaComponentNode extends FigmaNode {
  int opacity;
  boolean isMask;
  List<Effect> effects;
  String effectsStyleId;

  // Corner Related Properties

  int cornerRadius;
  int cornerSmoothing;
  int topLeftRadius;
  int topRightRadius;
  int bottomLeftRadius;
  int bottomRightRadius;

  // geometry related properties
  List<Fills> fills;
  String fillStyleId;
  List<Strokes> strokes;
  String strokeStyleId;
  int strokeWeight;
  StrokeJoin strokeJoin;
  StrokeAlign strokeAlign;
  List<String> dashPattern;
  List<String> strokeGeometry;
  StrokeCap strokeCap;
  int strokeMitterLimit;
  List<FillGeometry> FillGeometry;
  int strokeTopWeight;
  int strokeBottomWeight;
  int strokeLeftWeight;
  int strokeRightWeight;
  LayoutMode layoutMode;
  PrimaryAxisSizingMode primaryAxisSizingMode;
  CounterAxisSizingMode counterAxisSizingMode;
  PrimaryAxisAlignItems primaryAxisAlignItems;
  CounterAxisAlignItems counterAxisAlignItems;
  // padding properties
  int paddingLeft;
  int paddingRight;
  int paddingTop;
  int paddingBottom;
  int itemSpacing;
  boolean itemReverseZIndex;
  boolean strokesIncludedInLayout;
  // layoutGrids - need to check
  String gridStyleId;
  boolean clipsContent;
}
