package com.necleo.codemonkey.service;

import static com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes.RECTANGLE;

import com.necleo.codemonkey.enums.Language;
import com.necleo.codemonkey.lib.types.ASTNode;
import com.necleo.codemonkey.lib.types.FNode;
import com.necleo.codemonkey.lib.types.figma.FigmaRectangleNode;
import com.necleo.codemonkey.service.flutter.RectangleFlutterCGI;
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
  RectangleFlutterCGI rectangleFlutterCGI;

  @Override
  public Language getLanguage() {
    return Language.FLUTTER;
  }

  @Override
  public ASTNode generate(FNode fNode) {
    String genCode = "";
    if (fNode.getType() == RECTANGLE) {
      FigmaRectangleNode figmaRectangleNode = (FigmaRectangleNode) fNode;
      genCode = rectangleFlutterCGI.generate(figmaRectangleNode);
      log.debug(fNode.toString());
    }

    return ASTNode.builder().value(genCode).build();
  }
}
