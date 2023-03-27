 package com.necleo.codemonkey.service.flutter;

 import com.necleo.codemonkey.lib.types.FNode;
 import com.necleo.codemonkey.lib.types.figma.FigmaRectangleNode;
 import lombok.extern.slf4j.Slf4j;
 import org.springframework.stereotype.Service;

 @Service
 @Slf4j
 public class RectangleFlutterCGI implements FlutterCGI <FigmaRectangleNode>{

  public String generate(FigmaRectangleNode fNode) {

    String genCode = "";

    genCode += "\nContainer( \n";
    genCode += getHeight(fNode);
    genCode += getWidth(fNode);
    genCode += getBoxDecoration(fNode);

    genCode += "),\n";
    System.out.println(genCode); // end indent

    return genCode;
  }

  public String getHeight(FigmaRectangleNode fNode) {
    if (fNode.getHeight() != 0) {
      return "height:" + Integer.toString(fNode.getHeight()) + ",\n";
    }
    return "height:0,\n";
  }

  public String getWidth(FigmaRectangleNode fNode) {
    if (fNode.getWidth() != 0) {
      return "width:" + Integer.toString(fNode.getWidth()) + ",\n";
    }
    return "width:0,\n";
  }

  public String getBoxDecoration(FigmaRectangleNode fNode) {
    final String upperBoxDecoration = "decoration: BoxDecoration(\n";
    final String bottomBoxDecoration = "),\n";
    String genBoxDecoration = "";
    if (fNode.getBottomLeftRadius() != 0
        || fNode.getTopLeftRadius() != 0
        || fNode.getTopRightRadius() != 0
        || fNode.getBottomRightRadius() != 0) {
      genBoxDecoration += borderRadius(fNode);
    }
    if (fNode.getStrokeWeight() != 0) {
      genBoxDecoration += border(fNode);
    }
    return upperBoxDecoration+ genBoxDecoration + bottomBoxDecoration;
  }

  public String borderRadius(FigmaRectangleNode fNode) {
    final String upperBorderRadius = " borderRadius: BorderRadius.all(";
    final String bottomBorderRadius = "),\n";
    String topradiusL = " topLeft: Radius.circular(" + fNode.getTopLeftRadius() + "),\n";
    String topradiusR =
        " topRight: Radius.circular(" + Float.toString(fNode.getTopRightRadius()) + "),\n";
    String bottomradiusL =
        " bottomLeft:: Radius.circular(" + Float.toString(fNode.getBottomLeftRadius()) + "),\n";
    String bottomradiusR =
        " bottomRight: Radius.circular(" + Float.toString(fNode.getBottomRightRadius()) + "),\n";
    return upperBorderRadius
        + topradiusL
        + topradiusR
        + bottomradiusL
        + bottomradiusR
        + bottomBorderRadius;
  }

  public String border(FigmaRectangleNode fNode) {
    final String upperBorder = " border: Border.all(";
    final String bottomBorder = "),\n";
    final String width = "width:" + Float.toString(fNode.getStrokeWeight()) + ",\n";
    return upperBorder + width + bottomBorder;
  }
 }
