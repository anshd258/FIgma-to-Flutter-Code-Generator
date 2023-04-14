package com.necleo.codemonkey.service;

import com.necleo.codemonkey.enums.Language;
import com.necleo.codemonkey.factory.ReactFigmaNodeFactory;
import com.necleo.codemonkey.factory.TagDataNodeFactory;
import com.necleo.codemonkey.lib.types.ASTNode;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.service.react.ReactCGI;
import java.util.Map;
import java.util.Optional;

import com.necleo.codemonkey.service.react.ReactTaggedDataCGI;
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

  ReactFigmaNodeFactory figmaNodeFactory;
  TagDataNodeFactory tagDataNodeFactory;

  @Override
  public Language getLanguage() {
    return Language.REACT;
  }

  @Override
  public ASTNode generate(FigmaNode fNode, Map<String, TagData> tagDataMap) {
    String genCode = "";
    if (tagDataMap.get(0).getTagName().equals(""))
    {
    Optional<ReactCGI> reactCGIOptional = figmaNodeFactory.getProcessor(fNode.getType());
    genCode += reactCGIOptional.map(reactCGI -> reactCGI.generate(fNode)).orElse("");
    }
    else {
      Optional<ReactTaggedDataCGI> reactTaggedDataCGI = tagDataNodeFactory.getProcessor(fNode.getType(), tagDataMap);
      genCode += reactTaggedDataCGI.map(reacttaggedCGI -> reacttaggedCGI.generate(fNode)).orElse("");
    }
    return ASTNode.builder().value(genCode).build();
  }
}
