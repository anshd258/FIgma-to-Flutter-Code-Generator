package com.necleo.codemonkey.lib.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.figma.*;
import com.necleo.codemonkey.lib.types.figma.properties.Constrains;
import com.necleo.codemonkey.lib.types.figma.rect.Rect;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

/**
 * Type declaration of figma screen <br>
 * <br>
 * {@link #type} figma node type <br>
 *  figma node children
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
  @JsonSubTypes.Type(value = FigmaRectangleNode.class, name = "RECTANGLE"),
  @JsonSubTypes.Type(value = FigmaEllipseNode.class, name = "ELLIPSE"),
  @JsonSubTypes.Type(value = FigmaLineNode.class, name = "LINE"),
  @JsonSubTypes.Type(value = FigmaPolygonNode.class, name = "POLYGON"),
  @JsonSubTypes.Type(value = FigmaStarNode.class, name = "STAR"),
  @JsonSubTypes.Type(value = FigmaVectorNode.class, name = "VECTOR"),
  @JsonSubTypes.Type(value = FigmaFrameNode.class, name = "FRAME"),
        @JsonSubTypes.Type(value = FigmaFrameNode.class, name = "GROUP"),
  @JsonSubTypes.Type(value = FigmaComponentNode.class, name = "COMPONENT"),
  @JsonSubTypes.Type(value = FigmaTextNode.class, name = "TEXT")
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@SuperBuilder
@NoArgsConstructor
@Jacksonized
@ToString
public class FigmaNode {
  FigmaNodeTypes type;
  List<FigmaNode> child;
  String id;
  String name;
  Constrains constraints;
  boolean removed;
  boolean visible;
  boolean locked;
  int[][] absoluteTransform;
  int[][] relativeTransform;
  int x;
  int y;
  int rotation;
  int width;
  int height;
  Rect absoluteRenderBounds;
  Rect absoluteBoundingBox;
}
