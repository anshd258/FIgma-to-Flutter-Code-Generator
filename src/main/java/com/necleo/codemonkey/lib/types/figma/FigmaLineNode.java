package com.necleo.codemonkey.lib.types.figma;

import com.necleo.codemonkey.lib.types.FNode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.Effect;
import com.necleo.codemonkey.lib.types.figma.properties.fills.FillGeometry;
import com.necleo.codemonkey.lib.types.figma.properties.fills.Fills;
import com.necleo.codemonkey.lib.types.figma.properties.strokes.StrokeGeometry;
import com.necleo.codemonkey.lib.types.figma.properties.strokes.Strokes;
import com.necleo.codemonkey.lib.types.figma.rect.Rect;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FigmaLineNode extends FNode {

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

  @Builder
  public FigmaLineNode(
      String type,
      String id,
      String name,
      boolean removed,
      int[][] absoluteTransform,
      int[][] relativeTransform,
      int x,
      int y,
      int rotation,
      int width,
      int height,
      List<FNode> children,
      boolean visible,
      boolean locked,
      Rect absoluteRenderBounds,
      Rect absoluteBoundingbox,
      int opacity,
      boolean isMask,
      List<Effect> effects,
      String effectsStyleId,
      List<Fills> fills,
      String fillStyleId,
      List<Strokes> strokes,
      String strokeStyleId,
      int strokeWeight,
      String strokeJoin,
      String strokeAlign,
      List<String> dashPattern,
      List<StrokeGeometry> strokeGeometry,
      String strokeCap,
      int strokeMitterLimit,
      List<FillGeometry> FillGeometry,
      int strokeTopWeight,
      int strokeBottomWeight,
      int strokeLeftWeight,
      int strokeRightWeight) {
    super(
        type,
        children,
        id,
        name,
        removed,
        visible,
        locked,
        absoluteTransform,
        relativeTransform,
        x,
        y,
        rotation,
        width,
        height,
        absoluteRenderBounds,
        absoluteBoundingbox);
    this.opacity = opacity;
    this.isMask = isMask;
    this.effects = effects;
    this.effectsStyleId = effectsStyleId;
    this.fills = fills;
    this.fillStyleId = fillStyleId;
    this.strokes = strokes;
    this.strokeStyleId = strokeStyleId;
    this.strokeWeight = strokeWeight;
    this.strokeJoin = strokeJoin;
    this.strokeAlign = strokeAlign;
    this.dashPattern = dashPattern;
    this.strokeGeometry = strokeGeometry;
    this.strokeCap = strokeCap;
    this.strokeMitterLimit = strokeMitterLimit;
    this.FillGeometry = FillGeometry;
    this.strokeTopWeight = strokeTopWeight;
    this.strokeBottomWeight = strokeBottomWeight;
    this.strokeLeftWeight = strokeLeftWeight;
    this.strokeRightWeight = strokeRightWeight;
  }
}
