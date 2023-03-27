package com.necleo.codemonkey.lib.types.figma;

import com.necleo.codemonkey.lib.types.FNode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.*;
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
public class FigmaEllipseNode extends FNode {

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
  List<StrokeGeometry> strokeGeometry;
  StrokeCap strokeCap;
  int strokeMitterLimit;
  List<FillGeometry> FillGeometry;
  int strokeTopWeight;
  int strokeBottomWeight;
  int strokeLeftWeight;
  int strokeRightWeight;

  @Builder
  public FigmaEllipseNode(
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
      int cornerRadius,
      int cornerSmoothing,
      int topLeftRadius,
      int topRightRadius,
      int bottomLeftRadius,
      int bottomRightRadius,
      List<Fills> fills,
      String fillStyleId,
      List<Strokes> strokes,
      String strokeStyleId,
      int strokeWeight,
      StrokeJoin strokeJoin,
      StrokeAlign strokeAlign,
      List<String> dashPattern,
      List<StrokeGeometry> strokeGeometry,
      StrokeCap strokeCap,
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
    this.cornerRadius = cornerRadius;
    this.cornerSmoothing = cornerSmoothing;
    this.topLeftRadius = topLeftRadius;
    this.topRightRadius = topRightRadius;
    this.bottomLeftRadius = bottomLeftRadius;
    this.bottomRightRadius = bottomRightRadius;
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
