package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.factory.Factory;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import com.necleo.codemonkey.model.factory.NecleoDataNode;
import java.util.Set;

public interface FlutterCGI extends Factory<Set<FigmaNodeMapper>> {
  String generate(NecleoDataNode necleoDataNode);
}
