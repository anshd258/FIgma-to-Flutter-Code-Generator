package com.necleo.codemonkey.service;

import com.necleo.codemonkey.enums.Language;
import com.necleo.codemonkey.factory.ReactFigmaNodeAbstractFactory;
import com.necleo.codemonkey.lib.types.ASTNode;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.TagDataType;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
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

  ReactFigmaNodeAbstractFactory figmaNodeFactory;

  @Override
  public Language getLanguage() {
    return Language.REACT;
  }

  @Override
  public ASTNode generate(FigmaNode fNode, Map<String, TagData> tagDataMap) {
    String genCode = "";

    String tagName =
            Optional.ofNullable(tagDataMap.get(fNode.getId())).map(TagData::getTagName).orElse(null);
    FigmaNodeMapper figmaNodeMapper =
            new FigmaNodeMapper(fNode.getType(), TagDataType.valueOf(tagName.toUpperCase()));
    Optional<ReactCGI> reactCGIOptional = figmaNodeFactory.getProcessor(figmaNodeMapper);
    genCode += reactCGIOptional.map(reactCGI -> reactCGI.generate(fNode)).orElseThrow();
    return ASTNode.builder().value(genCode).build();
  }
}
