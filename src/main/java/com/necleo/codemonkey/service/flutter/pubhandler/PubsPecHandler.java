package com.necleo.codemonkey.service.flutter.pubhandler;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PubsPecHandler {

  public static final String FLUTTER = "flutter";

  @SneakyThrows
  public String generatePubSpec(@NonNull Set<String> packages, String name, String desc) {
    Map<String, Object> dependencies = getDependencies(packages);

    Map<String, Object> devDependencies = getDevDependencies();

    PubSpec pubSpec =
        PubSpec.builder()
            .name(name)
            .description(desc)
            .publishTo("'none'")
            .version("1.0.0+1")
            .environment(Environment.builder().sdk("'>=2.19.6 <3.0.0'").build())
            .dependencies(dependencies)
            .devDependencies((devDependencies))
            .flutter(FlutterPackage.builder().usesMaterialDesign(true).build())
            .build();

    YAMLMapper yamlMapper = new YAMLMapper();
    return yamlMapper.writeValueAsString(pubSpec);
  }

  private static Map<String, Object> getDependencies(@NonNull Set<String> packages) {

    Map<String, Object> dependencies = new HashMap<>(Map.of(FLUTTER, Map.of("sdk", FLUTTER)));
    packages.forEach(dependency -> mapDependency(dependencies, dependency));
    return dependencies;
  }

  private static void mapDependency(Map<String, Object> dependencies, String dependency) {
    if (dependency.equals("GOOGLE_FONTS")) {
      dependencies.put("google_fonts", "^4.0.4");
    } else if (dependency.equals("SVG_PATH_PARSER")) {
      dependencies.put("svg_path_parser", "^1.1.1");
    }
  }

  private static Map<String, Object> getDevDependencies() {
    return Map.of("flutter_test", Map.of("sdk", FLUTTER), "flutter_lints", "^2.0.0");
  }

  @Data
  @Builder
  @Jacksonized
  @FieldDefaults(level = AccessLevel.PRIVATE)
  private static class PubSpec {
    String name;
    String description;
    String publishTo;
    String version;
    Environment environment;
    Map<String, Object> dependencies;
    Map<String, Object> devDependencies;
    FlutterPackage flutter;
  }

  @Data
  @Builder
  @Jacksonized
  @FieldDefaults(level = AccessLevel.PRIVATE)
  private static class FlutterPackage {
    @JsonProperty("uses-material-design")
    boolean usesMaterialDesign;
  }

  @Data
  @Builder
  @Jacksonized
  @FieldDefaults(level = AccessLevel.PRIVATE)
  private static class Environment {
    String sdk;
  }
}
