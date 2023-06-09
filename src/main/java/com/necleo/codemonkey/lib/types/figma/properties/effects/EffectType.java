package com.necleo.codemonkey.lib.types.figma.properties.effects;

import com.necleo.codemonkey.lib.types.enums.figmaEnums.BlendMode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.Effect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EffectType {
  private Effect type;
  private EffectColor color;
  private OffsetType offset;
  private int radius;
  private int spread;
  private boolean visible;
  private BlendMode blendMode;
  private boolean showShadowBehindNode;
}
