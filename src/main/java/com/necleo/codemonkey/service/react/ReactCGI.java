package com.necleo.codemonkey.service.react;

import com.necleo.codemonkey.factory.Factory;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import java.util.Map;
import java.util.Set;

public interface ReactCGI extends Factory<Set<FigmaNodeMapper>> {
  String generate(
      FigmaNode fNode,
      FigmaNode node,
      Map<String, TagData> tagDataMap,
      Set<String> importsFunctions);
}
