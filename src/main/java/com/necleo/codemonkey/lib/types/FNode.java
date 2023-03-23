package com.necleo.codemonkey.lib.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.necleo.codemonkey.lib.types.interfaces.Rect;
import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

/**
 * Type declaration of figma screen <br>
 * <br>
 * {@link #type} figma node type <br>
 * {@link #children} figma node children
 */
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Jacksonized
public class FNode {
  String id;
  String type;
  int[][] absoluteTransform;
  int[][] relativeTransform;
  int x;
  int y;
  int rotation;
  int width;
  int height;
  List<FNode> children;
  boolean visible;
  boolean locked;
  Rect absoluteRenderBounds;
  Rect absoluteBoundingbox;
}
