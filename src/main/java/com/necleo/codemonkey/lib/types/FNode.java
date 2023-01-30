package com.necleo.codemonkey.lib.types;

import java.util.List;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * Type declaration of figma screen <br>
 * <br>
 * {@link #type} figma node type <br>
 * {@link #children} figma node children
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FNode {
  String type;
  List<FNode> children;
}
