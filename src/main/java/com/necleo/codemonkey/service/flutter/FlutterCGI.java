package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.factory.Factory;
import com.necleo.codemonkey.flutter.index.FlutterGI;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import com.necleo.codemonkey.model.factory.FlutterWI;
import java.util.Set;

public interface FlutterCGI extends Factory<Set<FigmaNodeMapper>> {
  String generate(FigmaNode figmaNode, FigmaNode parentFigmaNode, FlutterGI flutterGI, FlutterWI flutterWI);

  String generate(FlutterWI fultterNecleoDataNode, FigmaNode figmaNode);
}
