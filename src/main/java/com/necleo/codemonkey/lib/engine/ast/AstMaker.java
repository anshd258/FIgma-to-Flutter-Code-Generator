package com.necleo.codemonkey.lib.engine.ast;

import com.necleo.codemonkey.lib.types.ASTNode;
import com.necleo.codemonkey.lib.types.FigmaNode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AstMaker {

  public ASTNode ast(FigmaNode figmaNode) {
    return null;
  }

  private ASTNode astFileNode(FigmaNode figmaNode) {

    return null;
  }
}
