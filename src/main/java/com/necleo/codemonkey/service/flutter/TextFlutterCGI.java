package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.figma.FigmaTextNode;
import com.necleo.codemonkey.lib.types.figma.properties.fills.subtypes.FillsSolid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TextFlutterCGI implements FlutterCGI {

  @Override
  public String generate(FigmaNode figmaNode) {
    if (!(figmaNode instanceof FigmaTextNode fNode)) {
      throw new IllegalArgumentException();
    }
    return generat(fNode);
  }

  public String generat(FigmaTextNode fNode) {
    String genCode = "";
    final String upperText = "Text(\n";
    final String lowerText = "),\n";
    genCode += upperText;
    genCode += getText(fNode);
    genCode += getTextStyle(fNode);

    return genCode;
  }

  private String getTextStyle(FigmaTextNode fNode) {
    final String upperTextStyle = "style: const TextStyle(\n";
    final String lowerTextStyle = "),\n";
    String genTextStyle = "";
    if (fNode.getFills() != null) {
      final FillsSolid fills = (FillsSolid) fNode.getFills().get(0);
      genTextStyle += getColor(fills);
    }
    return upperTextStyle + genTextStyle + lowerTextStyle;
  }

  private String getColor(FillsSolid fills) {
    final String upperColor = "color: Color.fromRGBO(\n";
    final String lowerColor = "),\n";
    final String red = Math.round(fills.getColor().getR() * 255) + ",";
    final String green = Math.round(fills.getColor().getG() * 255) + ",";
    final String blue = Math.round(fills.getColor().getB() * 255) + ",";
    final String opacity = Float.toString(fills.getOpacity());

    return upperColor + red + green + blue + opacity + lowerColor;
  }

  private String getText(FigmaTextNode fNode) {
    if (fNode.getName() != null) {
      return fNode.getName() + "\n";
    }
    return "";
  }

  @Override
  public FigmaNodeTypes getEnumMapping() {
    return FigmaNodeTypes.TEXT;
  }
}
