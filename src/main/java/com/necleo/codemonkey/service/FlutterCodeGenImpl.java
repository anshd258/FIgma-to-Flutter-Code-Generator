package com.necleo.codemonkey.service;

import com.necleo.codemonkey.enums.Language;
import com.necleo.codemonkey.lib.types.ASTNode;
import com.necleo.codemonkey.lib.types.FNode;
import com.necleo.codemonkey.service.flutter.FigmaNodeFactory;
import com.necleo.codemonkey.service.flutter.FlutterCGI;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FlutterCodeGenImpl implements CodeGen {

  FigmaNodeFactory figmaNodeFactory;

  @Override
  public Language getLanguage() {
    return Language.FLUTTER;
  }

  @Override
  public ASTNode generate(FNode fNode) {
    String genCode = "";
    FlutterCGI flutterCGI = figmaNodeFactory.getNode(fNode);
    genCode += flutterCGI.generate(fNode);

    return ASTNode.builder().value(genCode).build();
  }
}
