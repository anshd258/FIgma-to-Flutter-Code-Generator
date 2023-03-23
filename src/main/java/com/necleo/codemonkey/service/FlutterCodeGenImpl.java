package com.necleo.codemonkey.service;

import com.necleo.codemonkey.enums.Language;
import com.necleo.codemonkey.lib.types.ASTNode;
import com.necleo.codemonkey.lib.types.FNode;
import org.springframework.stereotype.Service;

@Service
public class FlutterCodeGenImpl implements CodeGen {
  @Override
  public Language getLanguage() {
    return Language.FLUTTER;
  }

  @Override
  public ASTNode generate(FNode fNode) {
    String genResult = "";
    if (fNode.getType() == null) {
      throw new IllegalArgumentException("no type received");
    } else if (fNode.getType().equals("RECTANGLE") || fNode.getType().equals("ELLIPSE")) {

    }
    ASTNode build = ASTNode.builder().build();
    return build;
  }
}
