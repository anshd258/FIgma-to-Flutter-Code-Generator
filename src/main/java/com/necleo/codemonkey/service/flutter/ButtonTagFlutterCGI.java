package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.factory.FlutterFigmaNodeAbstractFactory;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.TagDataType;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;

import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Slf4j
// @RequiredArgsConstructor
// @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ButtonTagFlutterCGI implements FlutterCGI {
  @Lazy
  FlutterFigmaNodeAbstractFactory flutterFigmaNodeFactory;
  //  FlutterFigmaNodeAbstractFactory flutterFigmaNodeFactory;

  @Override
  public String generate(FigmaNode figmaNode, TagData tagData) {

    return generat(figmaNode, tagData);
  }

  private String generat(FigmaNode fNode, TagData tagData) {
    final String upperButton = "GestureDetector(\n";

    final String lowerButton = "),\n";
    String genCode = "";
    genCode += getFunction();
    genCode += getChild(fNode, tagData);

    return upperButton + genCode + lowerButton;
  }

  private String getChild(FigmaNode fNode, TagData tagData) {
    String genChild = "";
    FigmaNodeMapper figmaNodeMapper =
            new FigmaNodeMapper(fNode.getType(), null);
    Optional<FlutterCGI> flutterCGIOptional =
            flutterFigmaNodeFactory.getProcessor(figmaNodeMapper);
      genChild +=
              flutterCGIOptional
                      .map(flutterCGI -> flutterCGI.generate(fNode, null))
                      .orElse("");

    return "child:" + genChild + ",\n";
  }

  private String getFunction() {
    String genFunction = "";
    return " onTap: () {" + genFunction + "},\n";
  }

  @Override
  public Set<FigmaNodeMapper> getStrategy() {
    return Set.of(
        new FigmaNodeMapper(FigmaNodeTypes.FRAME, TagDataType.BUTTON),
        new FigmaNodeMapper(FigmaNodeTypes.RECTANGLE, TagDataType.BUTTON),
            new FigmaNodeMapper(FigmaNodeTypes.TEXT, TagDataType.BUTTON),
    new FigmaNodeMapper(FigmaNodeTypes.VECTOR, TagDataType.BUTTON),
            new FigmaNodeMapper(FigmaNodeTypes.LINE, TagDataType.BUTTON));
  }
}
