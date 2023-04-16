package com.necleo.codemonkey.service.react;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.figma.FigmaRectangleNode;
import com.necleo.codemonkey.lib.types.figma.properties.fills.subtypes.FillsImage;
import com.necleo.codemonkey.lib.types.figma.properties.fills.subtypes.FillsSolid;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class RectangleReactCGI implements ReactCGI {

  @Override
  public Set<FigmaNodeMapper> getStrategy() {
    return Set.of(new FigmaNodeMapper(FigmaNodeTypes.RECTANGLE, null));
  }

  @Override
  public String generate(FigmaNode figmaNode) {
    if (!(figmaNode instanceof FigmaRectangleNode fNode)) {
      throw new IllegalArgumentException();
    }
    return generat(fNode);
  }

  public String generat(FigmaNode figmaNode) {
    FigmaRectangleNode fNode = (FigmaRectangleNode) figmaNode;
    String genCode = "";

    if (fNode.getFills().get(0).getType().equals("IMAGE")) {
      genCode += getImageProps(fNode);
    }
    else {

      genCode += "\n<div style={{ \n";
      genCode += getRectangleStyles(figmaNode);
      genCode += " }}>";
      genCode += "</div>\n";
    }
    System.out.println(genCode); // end indent

    return genCode;
  }

  public String getRectangleStyles(FigmaNode figmaNode) {
    String rectStyles = "";
    FigmaRectangleNode fNode = (FigmaRectangleNode) figmaNode;
    rectStyles += getHeight(fNode);
    rectStyles += getWidth(fNode);
    rectStyles += getBackgroundColour(fNode);
    rectStyles += getBoxDecoration(fNode);
    rectStyles += getOpacity(fNode);
    rectStyles += getHorizontalPosition(fNode);
    rectStyles += getVerticalPosition(fNode);
    rectStyles += getVisibility(fNode);

    return rectStyles;
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
    final FillsSolid fills = (FillsSolid) fNode.getFills().get(0);
    final String begin = "backgroundColor: 'rgb(";
    final String end = ")',\n";
    final String fNodeColourR = (255 * fills.getColor().getR()) + ",";
    final String fNodeColourG = (255 * fills.getColor().getG()) + ",";
    final String fNodeColourB = String.valueOf(255 * fills.getColor().getB());
    return begin + fNodeColourR + fNodeColourG + fNodeColourB + end;
  }

  public String getImageProps(FigmaRectangleNode fNode) {
    return "<Image style={{" + getImageStyles(fNode) + "}" + getBackgroundImage(fNode) + "} />\n";
  }

  public String getImageStyles(FigmaRectangleNode fNode) {
    String imgStyles = "";
    final FillsImage fillsImage = (FillsImage) fNode.getFills().get(0);

    // alignments
    imgStyles +=
        getHorizontalPosition(fNode)
            + getVerticalPosition(fNode)
            + getHeight(fNode)
            + getWidth(fNode)
            + getOpacity(fNode);

    // decorations
    imgStyles += getBoxDecoration(fNode);

    // visibility
    imgStyles += getVisibility(fNode);

    // fit to screen or not
    imgStyles += getImgResize(fillsImage);

    return imgStyles;
  }

  public String getBackgroundImage(FigmaRectangleNode fNode) {
    final FillsImage fillsImage = (FillsImage) fNode.getFills().get(0);

    final String imageHash = fillsImage.getImageHash();
    return "source={{uri: '" + imageHash + "'}}, \n";
  }

  public String getImgResize(FillsImage fillsImage) {
    String resizeMode = "";
    if (fillsImage.getScaleMode().equals("FILL")) resizeMode = "cover";
    else if (fillsImage.getScaleMode().equals("FIT")) resizeMode = "contain";
    else if (fillsImage.getScaleMode().equals("TITLE")) resizeMode = "center";
    else if (fillsImage.getScaleMode().equals("STRETCH")) resizeMode = "stretch";
    else resizeMode = "repeat";

    return "resizeMode: '" + resizeMode + "',\n";
  }

  public String getBoxDecoration(FigmaRectangleNode fNode) {
    final String upperBoxDecoration = "boxSizing: '";
    final String bottomBoxDecoration = ",\n";
    String genBoxDecoration = "";
    if (fNode.getCornerRadius() != 0) {
      genBoxDecoration = "border-box', \n border-radius: '";
      genBoxDecoration = genBoxDecoration + borderRadius(fNode) + "px',\n";
    } else genBoxDecoration = "unset'";
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

  public String getOpacity(FigmaRectangleNode fNode) {
    return ("opacity: '" + (fNode.getOpacity()) + "',\n ");
  }
}
