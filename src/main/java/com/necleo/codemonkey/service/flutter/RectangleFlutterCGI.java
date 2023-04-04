package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.figma.FigmaRectangleNode;
import com.necleo.codemonkey.lib.types.figma.FigmaTextNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RectangleFlutterCGI implements FlutterCGI {

  @Override
  public FigmaNodeTypes getEnumMapping() {
    return FigmaNodeTypes.RECTANGLE;
  }
@Override
  public String generate(FigmaNode figmaNode) {
    if (!(figmaNode instanceof FigmaRectangleNode fNode)) {
      throw new IllegalArgumentException();
    }
    return generat(fNode);
  }




  public String generat(FigmaRectangleNode figmaNode) {

    String genCode = "";

    genCode += "\nContainer( \n";
    genCode += getHeight(figmaNode);
    genCode += getWidth(figmaNode);
    genCode += getBoxDecoration(figmaNode);

    genCode += ")\n";
    System.out.println(genCode); // end indent

    return genCode;
  }

  private String getHeight(FigmaRectangleNode fNode) {
    if (fNode.getHeight() != 0) {
      return "height:" + Integer.toString(fNode.getHeight()) + ",\n";
    }
    return "height:0,\n";
  }

  private String getWidth(FigmaRectangleNode fNode) {
    if (fNode.getWidth() != 0) {
      return "width:" + Integer.toString(fNode.getWidth()) + ",\n";
    }
    return "width:0,\n";
  }

  private String getBoxDecoration(FigmaRectangleNode fNode) {
    final String upperBoxDecoration = "decoration: BoxDecoration(\n";
    final String bottomBoxDecoration = "),\n";
    String genBoxDecoration = "";
    if (fNode.getFills().get(0).getColor() != null && fNode.getFills().get(0).getOpacity() != 0) {
      genBoxDecoration += color(fNode);
    }
    if (fNode.getBottomLeftRadius() != 0
        || fNode.getTopLeftRadius() != 0
        || fNode.getTopRightRadius() != 0
        || fNode.getBottomRightRadius() != 0) {
      genBoxDecoration += borderRadius(fNode);
    }
    if (fNode.getStrokeWeight() != 0) {
      genBoxDecoration += border(fNode);
    }
    return upperBoxDecoration + genBoxDecoration + bottomBoxDecoration;
  }

  private String color(FigmaRectangleNode fNode) {
    final String upperColor = "color: Color.fromRGBO(\n";
    final String lowerColor = "),\n";
    final String red = Math.round(fNode.getFills().get(0).getColor().getR() * 255) + ",";
    final String green = Math.round(fNode.getFills().get(0).getColor().getG() * 255) + ",";
    final String blue = Math.round(fNode.getFills().get(0).getColor().getB() * 255) + ",";
    final String opacity = Float.toString(fNode.getFills().get(0).getOpacity());

    return upperColor + red + green + blue + opacity + lowerColor;
  }

  private String borderRadius(FigmaRectangleNode fNode) {
    final String upperBorderRadius = " borderRadius: BorderRadius.only(";
    final String bottomBorderRadius = "),\n";
    String topradiusL = " topLeft: Radius.circular(" + fNode.getTopLeftRadius() + "),\n";
    String topradiusR =
        " topRight: Radius.circular(" + Float.toString(fNode.getTopRightRadius()) + "),\n";
    String bottomradiusL =
        " bottomLeft: Radius.circular(" + Float.toString(fNode.getBottomLeftRadius()) + "),\n";
    String bottomradiusR = " bottomRight: Radius.circular(" + fNode.getBottomRightRadius() + "),\n";
    return upperBorderRadius
        + topradiusL
        + topradiusR
        + bottomradiusL
        + bottomradiusR
        + bottomBorderRadius;
  }

  private String border(FigmaRectangleNode fNode) {
    final String upperBorder = " border: Border.all(";
    final String bottomBorder = "),\n";
    final String width = "width:" + Float.toString(fNode.getStrokeWeight()) + ",\n";
    return upperBorder + width + bottomBorder;
  }
}
