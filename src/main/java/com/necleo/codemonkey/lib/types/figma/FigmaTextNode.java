package com.necleo.codemonkey.lib.types.figma;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.*;
import com.necleo.codemonkey.lib.types.enums.text.TextAlignHorizontal;
import com.necleo.codemonkey.lib.types.enums.text.TextAlignVertical;
import com.necleo.codemonkey.lib.types.enums.text.TextAutoResize;
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
public class FigmaTextNode extends FigmaNode {
  int opacity;
  BlendMode blendMode;
  boolean isMask;
  List<Effect> effects;
  String effectsStyleId;
  // geometry related properties
  List<Fills> fills;
  String fillStyleId;
  List<Strokes> strokes;
  String strokeStyleId;
  int strokeWeight;
  StrokeJoin strokeJoin;
  StrokeAlign strokeAlign;
  List<String> dashPattern;
  List<StrokeGeometry> strokeGeometry;
  StrokeCap strokeCap;
  int strokeMitterLimit;
  List<FillGeometry> FillGeometry;
  boolean hasMissingFont;
  TextAlignHorizontal textAlignHorizontal;
  TextAlignVertical textAlignVertical;
  TextAutoResize textAutoResize;
  int paragraphIndent;
  int paragraphSpacing;
  boolean autoRename;
  String characters;
  String fontFamily;
  int fontWeight;
  int fontSize;
  int lineHeight;
  PrimaryAxisAlignItems primaryAxisAlignitems;

}
