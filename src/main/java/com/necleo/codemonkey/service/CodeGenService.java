package com.necleo.codemonkey.service;

import com.necleo.codemonkey.configuration.S3FileLoader;
import com.necleo.codemonkey.enums.Language;
import com.necleo.codemonkey.lib.engine.ast.AST2Text;
import com.necleo.codemonkey.lib.engine.ast.AstMaker;
import com.necleo.codemonkey.lib.types.ASTNode;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;

import java.util.List;
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

  S3FileLoader s3FileLoader;

  @Lookup
  public AstMaker astMaker() {
    return null;
  }

  @Lookup
  public AST2Text aST2Text() {
    return null;
  }

//  static{
//    log.info("adad");
//  }

  public String gen(List<FigmaNode> screen, Map<String, TagData> tagDataMap) {

    CodeGen processor = languageFactory.getCodeGenProcessor(Language.FLUTTER);

    ASTNode astNode = processor.generate(screen, tagDataMap);


    //    ASTNode astNode = astMaker().ast(screen);
    StringBuffer text = aST2Text().toText(astNode);
    log.info("Code gen : \n{}", text.toString());
    s3FileLoader.uploadFile(text.toString(),"dart","screen1","/project/lib/Screen/");
    return text.toString();
  }
}
