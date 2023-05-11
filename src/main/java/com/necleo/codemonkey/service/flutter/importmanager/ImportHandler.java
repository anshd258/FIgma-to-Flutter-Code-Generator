package com.necleo.codemonkey.service.flutter.importmanager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ImportHandler {
   public String getImports(Set<String> imports){
        String genImports = "";
       genImports += imports.stream().map(s -> switch (s) {
           case "GOOGLE_FONTS" -> "import 'package:google_fonts/google_fonts.dart';\n";
           case "MATERIALAPP" -> "import 'package:flutter/material.dart';\n";
           case "MYCLIPPER" -> "import './Utils/myclipper.dart';\n";
           default -> "";
       }).collect(Collectors.joining());
   return genImports+ "\n\n\n";
    }
}
