package com.necleo.codemonkey.service;

import com.necleo.codemonkey.enums.FigmaNodeType;
import com.necleo.codemonkey.enums.Language;
import com.necleo.codemonkey.lib.types.ASTNode;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.service.react.ReactCGI;
import com.necleo.codemonkey.service.react.ReactCGIFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ReactCodeGenImpl implements CodeGen {

  ReactCGIFactory reactCGIFactory;

  @Override
  public Language getLanguage() {
    return Language.REACT;
  }

  @Override
  public ASTNode generate(FigmaNode fNode) {
    FigmaNodeType figmaNodeType = FigmaNodeType.valueOf(fNode.getType().toUpperCase());
    ReactCGI reactCGI = reactCGIFactory.getCodeGenProcessor(figmaNodeType);
    reactCGI.generate(fNode);
    return null;
  }
}
