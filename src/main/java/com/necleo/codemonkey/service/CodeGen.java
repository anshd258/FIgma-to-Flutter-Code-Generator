package com.necleo.codemonkey.service;

import com.necleo.codemonkey.enums.Language;
import com.necleo.codemonkey.lib.types.ASTNode;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;

import java.util.List;
import java.util.Map;

public interface CodeGen {
  Language getLanguage();

  ASTNode generate(List<FigmaNode> fNode, Map<String, TagData> tagDataMap);
}
