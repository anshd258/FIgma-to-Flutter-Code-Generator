package com.necleo.codemonkey.service.react;

import com.necleo.codemonkey.enums.FigmaNodeType;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.figma.FigmaRectangleNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RectangleReactCGI implements ReactCGI {

  @Override
  public String generate(FigmaNode figmaNode) {
    FigmaRectangleNode fNode = (FigmaRectangleNode) figmaNode;
    String genCode = "";

    genCode += "\n<div style={{ \n";
    genCode += getHeight(fNode);
    genCode += getWidth(fNode);
    genCode += getBoxDecoration(fNode);
    genCode += opacity(fNode);
    genCode += getHorizontalPosition(fNode);
    genCode += getVerticalPosition(fNode);
    genCode += getVisibility(fNode);

    genCode += " }}>";
    genCode += "</div>\n";
    System.out.println(genCode); // end indent

    return genCode;
  }

  @Override
  public FigmaNodeType getFigmaNodeType() {
    return FigmaNodeType.RECTANGLE;
  }

  public String getHeight(FigmaRectangleNode fNode) {
    if (fNode.getHeight() != 0) {
      return "height: '" + fNode.getHeight() + "px',\n";
    }
    return "height: '0px',\n";
  }

  public String getWidth(FigmaRectangleNode fNode) {
    if (fNode.getWidth() != 0) {
      return "width: '" + fNode.getWidth() + "px',\n";
    }
    return "width: '0px',\n";
  }

  public String getBoxDecoration(FigmaRectangleNode fNode) {
    final String upperBoxDecoration = "boxSizing: '";
    final String bottomBoxDecoration = ",\n";
    String genBoxDecoration = "";
    if (fNode.getBottomLeftRadius() != 0
        || fNode.getTopLeftRadius() != 0
        || fNode.getTopRightRadius() != 0
        || fNode.getBottomRightRadius() != 0) {
      genBoxDecoration = "border-box', \n border-radius: '";
      genBoxDecoration = genBoxDecoration + borderRadius(fNode) + "px',\n";
    } else genBoxDecoration = "unset',\n";
    if (fNode.getStrokeWeight() != 0) {
      genBoxDecoration += border(fNode);
    }
    return upperBoxDecoration + genBoxDecoration + bottomBoxDecoration;
  }

  public String borderRadius(FigmaRectangleNode fNode) {
    //        final String upperBorderRadius = " borderRadius: BorderRadius.all(";
    //        final String bottomBorderRadius = "),\n";
    //        String topradiusL = " topLeft: Radius.circular(" + fNode.getTopLeftRadius() + "),\n";
    //        String topradiusR =
    //                " topRight: Radius.circular(" + Float.toString(fNode.getTopRightRadius()) +
    // "),\n";
    //        String bottomradiusL =
    //                " bottomLeft:: Radius.circular(" + Float.toString(fNode.getBottomLeftRadius())
    // + "),\n";
    //        String bottomradiusR =
    //                " bottomRight: Radius.circular(" +
    // Float.toString(fNode.getBottomRightRadius()) + "),\n";
    //        return upperBorderRadius
    //                + topradiusL
    //                + topradiusR
    //                + bottomradiusL
    //                + bottomradiusR
    //                + bottomBorderRadius;

    return Float.toString(fNode.getCornerRadius());
  }

  public String border(FigmaRectangleNode fNode) {
    final String fNodeColourR = (fNode.getFills().get(0).getColor().getR())+",";
    final String fNodeColourG = (fNode.getFills().get(0).getColor().getB())+",";
    final String fNodeColourB = String.valueOf(fNode.getFills().get(0).getColor().getB());

    final String upperBorder = "border: '";
    final String width = (fNode.getStrokeWeight()) + "px ";
    final String borderType = fNode.getStrokes().get(0).getType().toLowerCase() + " "; // this id must map the type of border ig
    final String colour = "rgb(" + fNodeColourR + fNodeColourG + fNodeColourB + ")";
    return upperBorder + width + borderType + colour ;
  }

  public String getHorizontalPosition(FigmaRectangleNode fNode) {
    return ("right: '" + (fNode.getX()) + "px',\n ");
  }

  public String getVerticalPosition(FigmaRectangleNode fNode) {
    return ("top: '" + (fNode.getY()) + "px',\n ");
  }

  public String getVisibility(FigmaRectangleNode fNode) {
    return ("visibility: " + fNode.isVisible() + ",\n");
  }

  public String opacity(FigmaRectangleNode fNode) {
    return ("opacity: '" + (fNode.getOpacity()) + "',\n ");
  }
}
