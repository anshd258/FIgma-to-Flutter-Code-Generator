package com.necleo.codemonkey.service;

import com.necleo.codemonkey.enums.Language;
import com.necleo.codemonkey.factory.FlutterFigmaNodeAbstractFactory;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import com.necleo.codemonkey.lib.types.ASTNode;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.TagDataType;
import com.necleo.codemonkey.service.flutter.FlutterCGI;
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

  FlutterFigmaNodeAbstractFactory flutterFigmaNodeFactory;

  @Override
  public Language getLanguage() {
    return Language.FLUTTER;
  }

  @Override
  public ASTNode generate(FigmaNode fNode, Map<String, TagData> tagDataMap) {

    String genCode = "";

    String tagName =
        Optional.ofNullable(tagDataMap.get(fNode.getId())).map(TagData::getTagName).orElse(null);
    FigmaNodeMapper figmaNodeMapper =
        new FigmaNodeMapper(fNode.getType(), TagDataType.valueOf(tagName.toUpperCase()));
    Optional<FlutterCGI> flutterCGIOptional = flutterFigmaNodeFactory.getProcessor(figmaNodeMapper);
    genCode += flutterCGIOptional.map(flutterCGI -> flutterCGI.generate(fNode)).orElse("");
    return ASTNode.builder().value(genCode).build();
  }
}
