package com.necleo.codemonkey.service;

import com.necleo.codemonkey.enums.Language;
import com.necleo.codemonkey.factory.FlutterFigmaNodeFactory;
import com.necleo.codemonkey.factory.FlutterTagDataNodeFactory;
import com.necleo.codemonkey.lib.types.ASTNode;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.TadDataType;
import com.necleo.codemonkey.service.flutter.FlutterCGI;
import com.necleo.codemonkey.service.flutter.TagFlutterCGI;
import java.util.Map;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FlutterCodeGenImpl implements CodeGen {

  FlutterFigmaNodeFactory flutterFigmaNodeFactory;
  FlutterTagDataNodeFactory flutterTagDataNodeFactory;

  @Override
  public Language getLanguage() {
    return Language.FLUTTER;
  }

  @Override
  public ASTNode generate(FigmaNode fNode, Map<String, TagData> tagDataMap) {
    String genCode = "";
    if (!tagDataMap.equals(null)) {
      Optional<TagFlutterCGI> tagFlutterCGIOptional =
          flutterTagDataNodeFactory.getProcessor(TadDataType.BUTTON);
      genCode +=
          tagFlutterCGIOptional.map(tagFlutterCGI -> tagFlutterCGI.generate(fNode, tagDataMap));
    } else {
      Optional<FlutterCGI> flutterCGIOptional =
          flutterFigmaNodeFactory.getProcessor(fNode.getType());
      genCode += flutterCGIOptional.map(flutterCGI -> flutterCGI.generate(fNode)).orElse("");
    }

    return ASTNode.builder().value(genCode).build();
  }
}
