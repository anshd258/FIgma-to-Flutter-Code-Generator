package com.necleo.codemonkey.service.flutter.importmanager;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ImportHandler {
  public String getPackageImports(Set<String> imports) {
    String genImports =
        imports.stream()
            .map(
                s ->
                    "import"
                        + switch (s) {
                          case "GOOGLE_FONTS" -> "'package:google_fonts/google_fonts.dart';\n";
                          case "MATERIAL_APP" -> "'package:flutter/material.dart';\n";
                          case "MY_CLIPPER" -> "'./Utils/myclipper.dart';\n";
                          default -> "";
                        })
            .collect(Collectors.joining());
    return genImports + "\n";
  }
}
