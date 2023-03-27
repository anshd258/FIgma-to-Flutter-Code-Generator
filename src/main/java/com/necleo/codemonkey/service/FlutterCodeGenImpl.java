package com.necleo.codemonkey.service;

import com.necleo.codemonkey.enums.Language;
import com.necleo.codemonkey.lib.types.ASTNode;
import com.necleo.codemonkey.lib.types.FNode;
import com.necleo.codemonkey.service.flutter.RectangleFlutterCGI;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
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
    if (Objects.equals(fNode.getType(), "RECTANGLE")) {
      genCode = rectangleFlutterCGI.generate(fNode);
    }

    return ASTNode.builder().value(genCode).build();
  }
}
