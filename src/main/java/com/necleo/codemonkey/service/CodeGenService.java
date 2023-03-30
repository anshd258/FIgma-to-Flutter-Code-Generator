package com.necleo.codemonkey.service;

import com.necleo.codemonkey.enums.Language;
import com.necleo.codemonkey.lib.engine.ast.AST2Text;
import com.necleo.codemonkey.lib.engine.ast.AstMaker;
import com.necleo.codemonkey.lib.types.FigmaNode;
import java.util.List;
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

  public String gen(List<FigmaNode> screen) {
    CodeGen processor = languageFactory.getCodeGenProcessor(Language.REACT);
    List<String> bufferList =
        screen.stream()
            .map(figmaNode -> generateAstNode(processor, figmaNode))
            .toList();
    return bufferList.toString();
  }

  private String generateAstNode(CodeGen processor, FigmaNode figmaNode) {
    return aST2Text().toText(processor.generate(figmaNode)).toString();
  }
}
