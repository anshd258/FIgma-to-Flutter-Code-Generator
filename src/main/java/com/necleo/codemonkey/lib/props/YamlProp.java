package com.necleo.codemonkey.lib.props;

import com.necleo.codemonkey.lib.types.enums.ASTAction;
import java.util.ArrayList;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class YamlProp {
  String type;
  String value;
  ASTAction action;
  List<YamlProp> children = new ArrayList<>();
}
