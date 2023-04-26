package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.factory.Factory;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import java.util.Set;

public interface FlutterCGI extends Factory<Set<FigmaNodeMapper>> {
  String generate(FigmaNode fNode, TagData tagData);
}
