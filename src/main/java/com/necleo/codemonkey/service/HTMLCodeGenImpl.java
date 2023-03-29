package com.necleo.codemonkey.service;

import com.necleo.codemonkey.enums.Language;
import com.necleo.codemonkey.lib.types.ASTNode;
import com.necleo.codemonkey.lib.types.FigmaNode;
import org.springframework.stereotype.Service;

@Service
public class HTMLCodeGenImpl implements CodeGen {
  @Override
  public Language getLanguage() {
    return Language.HTML_CSS;
  }

  @Override
  public ASTNode generate(FigmaNode fNode) {
    return null;
  }
}
