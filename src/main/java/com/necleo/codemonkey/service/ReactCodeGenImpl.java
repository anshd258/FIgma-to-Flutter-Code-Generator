package com.necleo.codemonkey.service;

import com.necleo.codemonkey.enums.Language;
import com.necleo.codemonkey.factory.ReactFigmaNodeAbstractFactory;
import com.necleo.codemonkey.lib.FileImportMapperReact.GenFileFunctions;
import com.necleo.codemonkey.lib.types.ASTNode;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.TagDataType;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import com.necleo.codemonkey.service.react.ReactCGI;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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

  GenFileFunctions genFileFunctions = new GenFileFunctions();

  @Override
  public Language getLanguage() {
    return Language.REACT;
  }

  @Override
  public ASTNode generate(FigmaNode fNode, Map<String, TagData> tagDataMap) {
    String genCode = "";
    Set<String> ImportsFunctions = new HashSet<>();

    String tagName =
            Optional.ofNullable(tagDataMap.get(fNode.getId())).map(TagData::getTagName).orElse(null);
    FigmaNodeMapper figmaNodeMapper =
            new FigmaNodeMapper(fNode.getType(), TagDataType.valueOf(tagName.toUpperCase()));
    Optional<ReactCGI> reactCGIOptional = figmaNodeFactory.getProcessor(figmaNodeMapper);
    genCode += reactCGIOptional.map(reactCGI -> reactCGI.generate(fNode, ImportsFunctions)).orElseThrow();
    String finalFile = genFileFunctions.genFile(ImportsFunctions, genCode);
    System.out.println(finalFile);
    return ASTNode.builder().value(genCode).build();
  }
}
