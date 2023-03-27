package com.necleo.codemonkey.lib.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.necleo.codemonkey.lib.types.figma.*;
import com.necleo.codemonkey.lib.types.figma.rect.Rect;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Type declaration of figma screen <br>
 * <br>
 * {@link #type} figma node type <br>
 * {@link #children} figma node children
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type",
    visible = true)
@JsonSubTypes({
  @JsonSubTypes.Type(value = FigmaRectangleNode.class, name = "RECTANGLE"),
  @JsonSubTypes.Type(value = FigmaEllipseNode.class, name = "ELLIPSE"),
  @JsonSubTypes.Type(value = FigmaLineNode.class, name = "LINE"),
  @JsonSubTypes.Type(value = FigmaPolygonNode.class, name = "POLYGON"),
  @JsonSubTypes.Type(value = FigmaStarNode.class, name = "STAR"),
  @JsonSubTypes.Type(value = FigmaVectorNode.class, name = "VECTOR"),
  @JsonSubTypes.Type(value = FigmaFrameNode.class, name = "FRAME"),
  @JsonSubTypes.Type(value = FigmaComponentNode.class, name = "COMPONENT")
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@NoArgsConstructor
@ToString
public class FNode {
  public String type;
  public List<FNode> children;
  public String id;
  public String name;
  public boolean removed;
  public boolean visible;
  public boolean locked;
  public int[][] absoluteTransform;
  public int[][] relativeTransform;
  public int x;
  public int y;
  public int rotation;
  public int width;
  public int height;
  public Rect absoluteRenderBounds;
  public Rect absoluteBoundingbox;

  public FNode(
      String type,
      List<FNode> children,
      String id,
      String name,
      boolean removed,
      boolean visible,
      boolean locked,
      int[][] absoluteTransform,
      int[][] relativeTransform,
      int x,
      int y,
      int rotation,
      int width,
      int height,
      Rect absoluteRenderBounds,
      Rect absoluteBoundingbox) {}
}
