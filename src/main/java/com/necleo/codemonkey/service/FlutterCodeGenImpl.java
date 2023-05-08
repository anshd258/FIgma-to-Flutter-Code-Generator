package com.necleo.codemonkey.service;

import com.necleo.codemonkey.enums.Language;
import com.necleo.codemonkey.factory.FlutterBoilerTypeAbstractFactory;
import com.necleo.codemonkey.factory.FlutterFigmaNodeAbstractFactory;
import com.necleo.codemonkey.lib.types.ASTNode;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.enums.boilerplate.BoilerType;
import com.necleo.codemonkey.model.factory.BoilerNodeMapper;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import com.necleo.codemonkey.model.factory.NecleoDataNode;
import com.necleo.codemonkey.service.flutter.FlutterCGI;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.necleo.codemonkey.service.flutter.boilerplate.BoilerCGI;
import com.necleo.codemonkey.service.flutter.importmanager.ImportHandler;
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

  FlutterFigmaNodeAbstractFactory flutterFigmaNodeFactory;
  FlutterBoilerTypeAbstractFactory flutterBoilerTypeAbstractFactory;

  ImportHandler importHandler = new ImportHandler();

  @Override
  public Language getLanguage() {
    return Language.FLUTTER;
  }

  @Override
  public ASTNode generate(List<FigmaNode> fNode, Map<String, TagData> tagDataMap) {
    NecleoDataNode necleoDataNode = new NecleoDataNode();
    necleoDataNode.fNode = fNode.get(0);
    necleoDataNode.tagData = tagDataMap.get(fNode.get(0).getId());
    necleoDataNode.imports = new HashSet<String>();
    necleoDataNode.imports.add("MATERIALAPP");
    String genCode = "";

    String tagName =
        Optional.ofNullable(tagDataMap.get(fNode.get(0).getId())).map(TagData::getTagName).orElse(null);
    FigmaNodeMapper figmaNodeMapper = new FigmaNodeMapper(fNode.get(0).getType(), null);
    Optional<FlutterCGI> flutterCGIOptional = flutterFigmaNodeFactory.getProcessor(figmaNodeMapper);
    genCode += flutterCGIOptional.map(flutterCGI -> flutterCGI.generate(necleoDataNode)).orElse("");
    BoilerNodeMapper boilerNodeMapper = new BoilerNodeMapper(BoilerType.STATELESS, null);
    Optional<BoilerCGI> boilerCGIOptional = flutterBoilerTypeAbstractFactory.getProcessor(boilerNodeMapper);
    String finalGenCode = genCode;
     String widget = boilerCGIOptional.map(boilerCGI -> boilerCGI.generate(finalGenCode)).orElse("");
    necleoDataNode.imports.forEach(log::info);
   String imports = importHandler.getImports(necleoDataNode.imports);
    return ASTNode.builder().value(imports + widget).build();
  }
}
