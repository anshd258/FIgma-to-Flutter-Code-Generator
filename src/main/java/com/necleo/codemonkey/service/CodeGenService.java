package com.necleo.codemonkey.service;

import com.necleo.codemonkey.enums.Language;
import com.necleo.codemonkey.lib.engine.ast.AST2Text;
import com.necleo.codemonkey.lib.engine.ast.AstMaker;
import com.necleo.codemonkey.lib.types.ASTNode;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import java.util.Map;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CodeGenService {

  LanguageFactory languageFactory;

  @Lookup
  public AstMaker astMaker() {
    return null;
  }

  @Lookup
  public AST2Text aST2Text() {
    return null;
  }

  public String gen(FigmaNode screen, Map<String, TagData> tagDataMap) {

    CodeGen processor = languageFactory.getCodeGenProcessor(Language.REACT);

    ASTNode astNode = processor.generate(screen, tagDataMap);
    //    ASTNode astNode = astMaker().ast(screen);
    StringBuffer text = aST2Text().toText(astNode);
    log.info("Code gen : \n{}", text.toString());
    return text.toString();
  }
}
