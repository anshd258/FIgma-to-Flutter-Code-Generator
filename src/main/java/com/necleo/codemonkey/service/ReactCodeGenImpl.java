package com.necleo.codemonkey.service;

import com.necleo.codemonkey.enums.Language;
import com.necleo.codemonkey.factory.ReactFigmaNodeAbstractFactory;
import com.necleo.codemonkey.lib.FileImportMapperReact.GenFileFunctions;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import com.necleo.codemonkey.service.react.ReactCGI;
import java.util.*;
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
  public void generate(List<FigmaNode> fNode, Map<String, TagData> tagDataMap) {
    String genCode = "";
    Set<String> importFunctions = new HashSet<>();

    Optional<ReactCGI> reactCGIOptional =
        figmaNodeFactory.getProcessor(FigmaNodeMapper.of(fNode.get(0), tagDataMap));
    genCode +=
        reactCGIOptional
            .map(reactCGI -> reactCGI.generate(fNode.get(0), null, tagDataMap, importFunctions))
            .orElseThrow();
    String finalFile = genFileFunctions.genFile(importFunctions, genCode);
    log.info(finalFile);
  }
}
