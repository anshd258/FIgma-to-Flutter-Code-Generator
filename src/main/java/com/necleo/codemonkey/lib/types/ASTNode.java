package com.necleo.codemonkey.lib.types;

import com.necleo.codemonkey.lib.types.enums.ASTType;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ASTNode {
  ASTType type;
  @Builder.Default
  List<ASTNode> children = new ArrayList<>();
  String value;
}
