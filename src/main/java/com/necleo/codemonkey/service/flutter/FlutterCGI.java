package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.factory.Factory;
import com.necleo.codemonkey.factory.mapper.FigmaNodeMapper;
import com.necleo.codemonkey.lib.types.FigmaNode;
import java.util.Set;

public interface FlutterCGI extends Factory<Set<FigmaNodeMapper>> {
  String generate(FigmaNode fNode);
}
