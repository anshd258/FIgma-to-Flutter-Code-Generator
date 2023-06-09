package com.necleo.codemonkey.model.factory;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.necleo.codemonkey.lib.types.enums.boilerplate.BoilerType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Getter
@Builder
@EqualsAndHashCode
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FlutterWI {

  FigmaNode mainScreen;
  Map<String, TagData> tagData;

  @Builder.Default Set<String> imports = new HashSet<>();

  @Builder.Default Map<String, String> functionData = new HashMap<>();

  @Setter
  @NonFinal
  BoilerType boilerType;
}
