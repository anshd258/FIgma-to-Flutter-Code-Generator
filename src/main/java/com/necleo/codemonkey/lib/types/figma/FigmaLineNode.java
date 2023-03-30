package com.necleo.codemonkey.lib.types.figma;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.Effect;
import com.necleo.codemonkey.lib.types.figma.properties.fills.FillGeometry;
import com.necleo.codemonkey.lib.types.figma.properties.fills.Fills;
import com.necleo.codemonkey.lib.types.figma.properties.strokes.StrokeGeometry;
import com.necleo.codemonkey.lib.types.figma.properties.strokes.Strokes;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
@Jacksonized
public class FigmaLineNode extends FigmaNode {

  int opacity;
  boolean isMask;
  List<Effect> effects;
  String effectsStyleId;

  // geometry related properties
  List<Fills> fills;
  String fillStyleId;
  List<Strokes> strokes;
  String strokeStyleId;
  int strokeWeight;
  String strokeJoin; // actually an enum will have to look it up
  String strokeAlign; // also an enum
  List<String> dashPattern;
  List<StrokeGeometry> strokeGeometry;
  String strokeCap; // also an enum
  int strokeMitterLimit;
  List<FillGeometry> FillGeometry;
  int strokeTopWeight;
  int strokeBottomWeight;
  int strokeLeftWeight;
  int strokeRightWeight;
}
