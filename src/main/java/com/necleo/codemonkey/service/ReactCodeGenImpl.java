package com.necleo.codemonkey.service;

import com.necleo.codemonkey.enums.Language;
import com.necleo.codemonkey.factory.FigmaNodeFactory;
import com.necleo.codemonkey.lib.types.ASTNode;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.service.react.ReactCGI;
import java.util.Map;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ReactCodeGenImpl implements CodeGen {

  FigmaNodeFactory figmaNodeFactory;

  @Override
  public Language getLanguage() {
    return Language.REACT;
  }

  @Override
  public ASTNode generate(FigmaNode fNode, Map<String, TagData> tagDataMap) {
    String genCode = "";
    Optional<ReactCGI> flutterCGIOptional = figmaNodeFactory.getProcessor(fNode.getType());
    genCode += flutterCGIOptional.map(reactCGI -> reactCGI.generate(fNode)).orElse("");
    return ASTNode.builder().value(genCode).build();
  }
}
