package com.necleo.codemonkey.service;

import com.necleo.codemonkey.enums.Language;
import com.necleo.codemonkey.lib.types.ASTNode;
import com.necleo.codemonkey.lib.types.FigmaNode;

public interface CodeGen {
  Language getLanguage();

  ASTNode generate(FigmaNode fNode);
}
