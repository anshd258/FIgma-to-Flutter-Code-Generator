package com.necleo.codemonkey.lib.types.figma.rect;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Rect {
  int x;

  int y;

  int width;

  int height;
}
