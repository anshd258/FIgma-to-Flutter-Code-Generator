package com.necleo.codemonkey.lib.types.enums.figmaEnums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Paint {
  SolidPaint,
  GradientPaint,
  ImagePaint,
  VideoPaint;
}
