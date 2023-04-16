package com.necleo.codemonkey.lib.types.figma.properties.text;

import com.necleo.codemonkey.lib.types.enums.text.TextAlignHorizontal;
import com.necleo.codemonkey.lib.types.enums.text.TextAlignVertical;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Jacksonized
public class Style {
  String fontFamily;
  String fontPostScriptName;
  int fontWeight;
  int fontSize;
  TextAlignVertical textAlignVertical;
  TextAlignHorizontal textAlignHorizontal;
  int letterSpacing;
  float lineHeightPx;
  float lineHeightPercent;
}
