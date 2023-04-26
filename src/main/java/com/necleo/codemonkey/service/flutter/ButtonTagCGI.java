package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.TagDataType;
import com.necleo.codemonkey.lib.types.figma.FigmaRectangleNode;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ButtonTagCGI implements FlutterCGI {

  RectangleFlutterCGI rectangleFlutterCGI = new RectangleFlutterCGI();
  TextFlutterCGI textFlutterCGI = new TextFlutterCGI();

  @Override
  public String generate(FigmaNode figmaNode) {

    return generat(figmaNode);
  }

  private String generat(FigmaNode fNode) {
    final String upperButton = "GestureDetector(\n";

    final String lowerButton = "),\n";
    String genCode = "";
    genCode += getFunction();
    genCode += getChild(fNode);

    return upperButton + genCode + lowerButton;
  }

  private String getChild(FigmaNode fNode) {
    String genChild = "";
    if (fNode.getType().equals(FigmaNodeTypes.RECTANGLE)) {
      genChild += rectangleFlutterCGI.generat((FigmaRectangleNode) fNode);
    } else if (fNode.getType().equals(FigmaNodeTypes.TEXT)) {
      genChild += textFlutterCGI.generate(fNode);
    }
    return "child:" + genChild + ",\n";
  }

  private String getFunction() {
    String genFunction = "";
    return " onTap: () {" + genFunction + "}\n";
  }

  @Override
  public Set<FigmaNodeMapper> getStrategy() {
    return Set.of(
        new FigmaNodeMapper(FigmaNodeTypes.FRAME, TagDataType.BUTTON),
        new FigmaNodeMapper(FigmaNodeTypes.RECTANGLE, TagDataType.BUTTON));
  }
}
