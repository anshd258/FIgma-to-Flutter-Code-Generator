package com.necleo.codemonkey.flutter.index;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FlutterGI {

  @NonFinal
  @Setter
  @Builder.Default boolean responsive = false;
  @Builder.Default Set<String> pubSpecPackages = new HashSet<>();
  @Builder.Default Set<String> assets = new HashSet<>();
  @Builder.Default Map<String, String> utilClass = new HashMap<>();
}
