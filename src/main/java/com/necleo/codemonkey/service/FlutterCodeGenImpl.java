package com.necleo.codemonkey.service;

import com.necleo.codemonkey.enums.Language;
import com.necleo.codemonkey.lib.types.ASTNode;
import com.necleo.codemonkey.lib.types.FigmaNode;
import java.util.Objects;

public class FlutterCodeGenImpl implements CodeGen {
  @Override
  public Language getLanguage() {
    return Language.FLUTTER;
  }

  @Override
  public ASTNode generate(FigmaNode fNode) {
    if (Objects.equals(fNode.getType(), "RECTANGLE")) {}

    return null;
  }
}
