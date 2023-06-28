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

/**
 * This class defines the Global configuration of the project we are going to build. <br>
 * <br>
 * {@link #responsive} defines weather we are using responsive sizer or not. <br>
 * {@link #pubSpecPackages} defines the packages we have defined till now. <br>
 * {@link #assets} defines a map of assets with their paths declared in pubsec.yaml file. <br>
 * {@link #utilClass} defines a map of util classes we have created for that project and imported
 * them Globally.
 */
@Getter
@Builder
@Jacksonized
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FlutterGI {

  @NonFinal @Setter @Builder.Default boolean responsive = false;
  @Builder.Default Set<String> pubSpecPackages = new HashSet<>();
  @Builder.Default Set<String> assets = new HashSet<>();
  @Builder.Default Map<String, String> utilClass = new HashMap<>();
}
