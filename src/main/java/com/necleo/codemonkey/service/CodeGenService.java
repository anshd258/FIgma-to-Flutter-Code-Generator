package com.necleo.codemonkey.service;

import com.necleo.codemonkey.lib.engine.ast.AST2Text;
import com.necleo.codemonkey.lib.engine.ast.AstMaker;
import com.necleo.codemonkey.lib.types.ASTNode;
import com.necleo.codemonkey.lib.types.FNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CodeGenService {

  @Lookup
  public AstMaker astMaker() {
    return null;
  }

  @Lookup
  public AST2Text aST2Text() {
    return null;
  }

  public String gen(FNode screen) {
    ASTNode astNode = astMaker().ast(screen);
    StringBuffer text = aST2Text().toText(astNode);
    log.info("Code gen : \n{}", text.toString());
    return text.toString();
  }
}
