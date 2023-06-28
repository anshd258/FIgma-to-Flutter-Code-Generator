package com.necleo.codemonkey.service;

import static com.necleo.codemonkey.flutter.enums.PackageImport.MATERIAL_APP;

import com.necleo.codemonkey.configuration.S3FileLoader;
import com.necleo.codemonkey.enums.Language;
import com.necleo.codemonkey.factory.FlutterBoilerPLateFactory;
import com.necleo.codemonkey.factory.FlutterFigmaWidgetFactory;
import com.necleo.codemonkey.flutter.index.FileIndex;
import com.necleo.codemonkey.flutter.index.FlutterGI;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.enums.boilerplate.BoilerType;
import com.necleo.codemonkey.model.factory.BoilerNodeMapper;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import com.necleo.codemonkey.model.factory.FlutterWI;
import com.necleo.codemonkey.service.flutter.FlutterCGI;
import com.necleo.codemonkey.service.flutter.MainClassMaker;
import com.necleo.codemonkey.service.flutter.boilerplate.BoilerCGI;
import com.necleo.codemonkey.service.flutter.importmanager.ImportHandler;
import com.necleo.codemonkey.service.flutter.pubhandler.PubsPecHandler;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** This class is to generate Flutter Code from Figma Object. */
@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FlutterCodeGenImpl implements CodeGen {

  S3FileLoader s3FileLoader;
  FlutterFigmaWidgetFactory flutterFigmaWidgetFactory;
  FlutterBoilerPLateFactory flutterBoilerPLateFactory;
  MainClassMaker mainClassMaker;
  ImportHandler importHandler;
  PubsPecHandler pubspecHandler;

  @Override
  public Language getLanguage() {
    return Language.FLUTTER;
  }

  /**
   * TODO: To get FlutterGI from S3. Returning project Global Index from S3.
   *
   * @return Closure of object of {@link FlutterGI}
   */
  Index<FlutterGI> loadFlutterGI() {
    return () -> FlutterGI.builder().build();
  }

  /**
   * This class is to generate Flutter Code from Figma Object.
   *
   * @param figmaNodes List of Figma Screen Nodes
   * @param tagDataMap Map of Figma Node Id mapped with Tag Data of that Figma Node.
   */
  @Override
  public void generate(List<FigmaNode> figmaNodes, Map<String, TagData> tagDataMap) {
    FlutterGI flutterGI = loadFlutterGI().getIndex();
    figmaNodes.forEach(figmaNode -> generate(figmaNode, flutterGI, tagDataMap));
    handlePubSpec(flutterGI);
    handleMainAppFile();
  }

  private void generate(FigmaNode figmaNode, FlutterGI flutterGI, Map<String, TagData> tagDataMap) {
    FlutterWI dataNode = FlutterWI.builder().mainScreen(figmaNode).tagData(tagDataMap).build();
    String genCode = generateWidget(figmaNode, flutterGI, dataNode);
    String widget = wrapFile(genCode);
    String imports = importHandler.getPackageImports(dataNode.getImports());
    String finalCode = imports + widget;
  }

  private void handleMainAppFile() {
    String mainImports =
        """
           import 'package:flutter/material.dart';
           import './Screen/screen1.dart';

           """;
    String mainDart = mainClassMaker.uploadMain(mainImports, "MainApp()");
    s3FileLoader.uploadFile(mainDart, "dart", "main", "/project/lib/");
  }

  private void handlePubSpec(FlutterGI flutterGI) {
    String pubSpec =
        pubspecHandler.generatePubSpec(flutterGI.getPubSpecPackages(), "test app", "loren ipsum ");

    // todo: refactor below line
    s3FileLoader.uploadFile(pubSpec, "yaml", "pubspec", "/project/");
  }

  private String wrapFile(String genCode) {
    FileIndex fileIndex = FileIndex.builder().stateType(BoilerType.STATELESS).build();
    Optional<BoilerCGI> boilerCGIOptional =
        flutterBoilerPLateFactory.getProcessor(
            new BoilerNodeMapper(fileIndex.getStateType(), null));
    return boilerCGIOptional.map(boilerCGI -> boilerCGI.generate(genCode)).orElse("");
  }

  private String generateWidget(FigmaNode figmaNode, FlutterGI flutterGI, FlutterWI dataNode) {
    dataNode.getImports().add(MATERIAL_APP.name());
    Optional<FlutterCGI> flutterCGIOptional =
        flutterFigmaWidgetFactory.getProcessor(
            FigmaNodeMapper.of(figmaNode, dataNode.getTagData()));
    return flutterCGIOptional
        .map(flutterCGI -> flutterCGI.generate(figmaNode, null, flutterGI, dataNode))
        .orElse("");
  }
}
