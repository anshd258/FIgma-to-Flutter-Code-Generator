package com.necleo.codemonkey.service.react;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.figma.FigmaRectangleNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RectangleReactCGI implements ReactCGI {

  @Override
  public FigmaNodeTypes getEnumMapping() {
    return FigmaNodeTypes.RECTANGLE;
  }

  @Override
  public String generate(FigmaNode figmaNode) {
    FigmaRectangleNode fNode = (FigmaRectangleNode) figmaNode;
    String genCode = "";

    genCode += "\n<div style={{ \n";
    genCode += getHeight(fNode);
    genCode += getWidth(fNode);
    genCode += getBackgroundColour(fNode);
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

  public String getBackgroundColour(FigmaRectangleNode fNode) {
    final String begin = "backgroundColor: 'rgb(";
    final String end = ")',\n";
    final String fNodeColourR = (255 * fNode.getFills().get(0).getColor().getR()) + ",";
    final String fNodeColourG = (255 * fNode.getFills().get(0).getColor().getG()) + ",";
    final String fNodeColourB = String.valueOf(255*fNode.getFills().get(0).getColor().getB());
    return begin + fNodeColourR + fNodeColourG + fNodeColourB + end;
  }

  public String getBoxDecoration(FigmaRectangleNode fNode) {
    final String upperBoxDecoration = "boxSizing: '";
    final String bottomBoxDecoration = ",\n";
    String genBoxDecoration = "";
    if (fNode.getCornerRadius() != 0) {
      genBoxDecoration = "border-box', \n border-radius: '";
      genBoxDecoration = genBoxDecoration + borderRadius(fNode) + "px',\n";
    } else genBoxDecoration = "unset',\n";
    if (fNode.getStrokeWeight() != 1) {
      genBoxDecoration += border(fNode);
    }

    return upperBoxDecoration + genBoxDecoration + bottomBoxDecoration;
  }

  public String borderRadius(FigmaRectangleNode fNode) {

    return Float.toString(fNode.getCornerRadius());
  }

  public String border(FigmaRectangleNode fNode) {
    final String fNodeColourR = (255 * fNode.getStrokes().get(0).getColor().getR()) + ",";
    final String fNodeColourG = (255 * fNode.getStrokes().get(0).getColor().getG()) + ",";
    final String fNodeColourB = String.valueOf(255 * fNode.getStrokes().get(0).getColor().getB());

    final String upperBorder = "border: '";
    final String width = (fNode.getStrokeWeight()) + "px ";
    final String borderType =
        fNode.getStrokes().get(0).getType().toLowerCase()
            + " "; // this id must map the type of border ig
    final String colour = "rgb(" + fNodeColourR + fNodeColourG + fNodeColourB + ")'";
    return upperBorder + width + borderType + colour;
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
