package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.factory.mapper.FigmaNodeMapper;
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
    final String upperTextStyle = "style: const  GoogleFonts." + fNode.getFontFamily() + "(\n";
    final String lowerTextStyle = "),\n";
    String genTextStyle = "";
    if (fNode.getFills() != null) {
      final FillsSolid fills = (FillsSolid) fNode.getFills().get(0);
      genTextStyle += getColor(fills);
    }
    if (fNode.getFontFamily() != null) {
      genTextStyle += getFontStyle(fNode);
      genTextStyle += getFontWeight(fNode);
      //      genTextStyle += getLetterSpacing(fNode);
      genTextStyle += getLineHeight(fNode);
    }
    return upperTextStyle + genTextStyle + lowerTextStyle;
  }

  private String getLineHeight(FigmaTextNode fNode) {
    return "height:" + fNode.getLineHeight() + ",\n";
  }

  //  private String getLetterSpacing(FigmaTextNode fNode) {
  //    return " letterSpacing:" + fNode.getLetterSpacing() +",\n";
  //  }

  private String getFontWeight(FigmaTextNode fNode) {
    return "fontWeight: FontWeight.w" + fNode.getFontWeight() + ",\n";
  }

  private String getFontStyle(FigmaTextNode fNode) {
    return "fontSize:" + fNode.getFontSize() + ",\n";
  }

  private String getColor(FillsSolid fills) {
    final String upperColor = "color: Color.fromRGBO(";
    final String lowerColor = "),\n";
    final String red = Math.round(fills.getColor().getR() * 255) + ",";
    final String green = Math.round(fills.getColor().getG() * 255) + ",";
    final String blue = Math.round(fills.getColor().getB() * 255) + ",";
    final String opacity = Float.toString(fills.getOpacity());

    return upperColor + red + green + blue + opacity + lowerColor;
  }

  private String getText(FigmaTextNode fNode) {
    if (fNode.getName() != null) {
      return "'" + fNode.getName() + "',\n";
    }
    return "";
  }

  @Override
  public FigmaNodeMapper getEnumMapping() {
    return new FigmaNodeMapper(FigmaNodeTypes.TEXT,null);
  }
}
