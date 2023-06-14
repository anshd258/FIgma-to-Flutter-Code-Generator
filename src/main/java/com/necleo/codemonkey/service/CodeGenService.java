package com.necleo.codemonkey.service;

import com.necleo.codemonkey.enums.Language;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CodeGenService {

  LanguageFactory languageFactory;

  public void generate(List<FigmaNode> screen, Map<String, TagData> tagDataMap) {
    CodeGen processor = languageFactory.getCodeGenProcessor(Language.FLUTTER);
    processor.generate(screen, tagDataMap);
  }
}
