package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.figma.FigmaTextNode;
import com.necleo.codemonkey.lib.types.figma.properties.fills.subtypes.FillsSolid;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import com.necleo.codemonkey.model.factory.NecleoDataNode;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TextFlutterCGI implements FlutterCGI {


  SizeUtil sizeUtil = new SizeUtil();
  @Override
  public Set<FigmaNodeMapper> getStrategy() {

    return Set.of(new FigmaNodeMapper(FigmaNodeTypes.TEXT, null));
  }

  @Override
  public String generate(NecleoDataNode necleoDataNode) {
    if (!(necleoDataNode.fNode instanceof FigmaTextNode fNode)) {
      throw new IllegalArgumentException();
    }
    return generat(fNode, necleoDataNode);
  }

  public String generat(FigmaTextNode fNode, NecleoDataNode necleoDataNode) {
    String genCode = "";
    final String outterContainer = " Align(\n" +
            "            alignment: Alignment.topLeft,\n" +
            "            child: Container(";
    String height = sizeUtil.getHeight(fNode);
    String width = sizeUtil.getWidth(fNode);
    final String upperText = "child: Text(\n";
    final String lowerText = "),),),\n";

    genCode += getText(fNode);
    genCode += getTextAlign(fNode);
    genCode += getTextStyle(fNode,necleoDataNode );

    return outterContainer + height + width + upperText + genCode + lowerText;
  }

  private String getTextStyle(FigmaTextNode fNode, NecleoDataNode necleoDataNode) {
    if(!necleoDataNode.imports.contains("GOOGLE_FONTS")){
      necleoDataNode.imports.add("GOOGLE_FONTS");
      // necleoDataNode.packages.add("GOOGLE_FONTS");
    }

    final String upperTextStyle = "style: GoogleFonts." + fNode.getFontName().getFamily().replace(" ", "").toLowerCase() + "(\n";
    final String lowerTextStyle = "),\n";
    String genTextStyle = "";
    if (fNode.getFills() != null) {
      final FillsSolid fills = (FillsSolid) fNode.getFills().get(0);
      genTextStyle += getColor(fills);
    }
    if (fNode.getFontSize() != 0) {
      genTextStyle += getFontStyle(fNode);
      genTextStyle += getFontWeight(fNode);
            genTextStyle += getLetterSpacing(fNode);
//            genTextStyle += getLineHeight(fNode);
    }
    return upperTextStyle + genTextStyle + lowerTextStyle;
  }

//  private String getLineHeight(FigmaTextNode fNode) {
//    return "height:" + (fNode.getLineHeight().getValue() - fNode.getFontSize()) + ",\n";
//  }

    private String getLetterSpacing(FigmaTextNode fNode) {
      return " letterSpacing:" + fNode.getLetterSpacing().getValue() +",\n";
    }

  private String getFontWeight(FigmaTextNode fNode) {
    return "fontWeight: FontWeight.w" + fNode.getFontWeight() + ",\n";
  }

  private String getFontStyle(FigmaTextNode fNode) {
    return "fontSize:" + (fNode.getFontSize() -1 )+ ",\n";
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
  private String getTextAlign(FigmaTextNode fNode){
    String align = "textAlign: TextAlign. ";
    String alignment = switch (fNode.getTextAlignHorizontal()) {
      case "CENTER" -> "center";
      case "LEFT" -> "left";
      case "RIGHT" -> "right";
      default -> "";
    };
    return align + alignment + ",\n";
  }

  private String getText(FigmaTextNode fNode) {
    if (fNode.getName() != null) {
      return "'" + fNode.getCharacters() + "',\n";
    }
    return "";
  }
}
