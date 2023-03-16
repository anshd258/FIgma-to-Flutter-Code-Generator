package com.necleo.codemonkey.lib.types;

import com.necleo.codemonkey.lib.types.interfaces.Rect;
import java.util.List;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

/**
 * Type declaration of figma screen <br>
 * <br>
 * {@link #type} figma node type <br>
 * {@link #children} figma node children
 */
@Builder
@Jacksonized
public record FNode(
    String id,
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
    Rect absoluteBoundingbox) {}
