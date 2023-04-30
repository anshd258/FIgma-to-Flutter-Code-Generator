package com.necleo.codemonkey.service.react;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.figma.FigmaFrameNode;
import com.necleo.codemonkey.lib.types.figma.properties.fills.subtypes.FillsSolid;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FrameReactCGI implements ReactCGI {

  RectangleReactCGI rectangleReactCGI = new RectangleReactCGI();
  TextReactCGI textReactCGI = new TextReactCGI();

  @Override
  public String generate(
      FigmaNode figmaNode,
      FigmaNode parentNode,
      Map<String, TagData> tagDataMap,
      Set<String> importsFunctions) {
    FigmaFrameNode fNode = (FigmaFrameNode) figmaNode;
    String genCode = "";

    genCode += "\n<div className='container' style={{ \n";
    if (parentNode != null) {
      genCode += getParentSpecialStyles((FigmaFrameNode) parentNode);
    }
    genCode += getStyles(figmaNode);
    genCode += " }}>";

    for (FigmaNode childNode : fNode.getChild()) {
      genCode += getChild(childNode, figmaNode);
    }
    genCode += "</div>\n";
    System.out.println(genCode); // end indent

    return genCode;
  }

  private String getParentSpecialStyles(FigmaFrameNode parentNode) {
    String parentStyles = "";
    if (parentNode.getLayoutMode().equals("HORIZONTAL")) {
      parentStyles += "flexDirection: 'row',\n";
    } else if (parentNode.getLayoutMode().equals("HORIZONTAL")) {
      parentStyles += "flexDirection: 'column',\n";
    } else {
      parentStyles += "\n";
    }
    return parentStyles;
  }

  public String getStyles(FigmaNode figmaNode) {
    FigmaFrameNode fNode = (FigmaFrameNode) figmaNode;
    String styles = "";
    styles += getWidth(fNode);
    styles += getHeight(fNode);
    styles += getBackgroundColour(fNode);
    styles += getBoxDecoration(fNode);
    styles += getOpacity(fNode);
    styles += getHorizontalPosition(fNode);
    styles += getVerticalPosition(fNode);
    styles += getVisibility(fNode);
    // genCode += generate(childrenNode);
    if (fNode.getLayoutMode().equals("AUTO")) {
      styles += getAutoLayout(fNode);
    }
    styles += "gap: '" + fNode.getItemSpacing() + "px',\n";
    styles += getPadding(fNode);
    return styles;
  }

  public String getAutoLayout(FigmaFrameNode fNode) {
    String autoLS = "";
    autoLS += "width: full,\n";
    autoLS += "height: 100%,\n";
    autoLS += "display: flex";

    return autoLS;
  }

  public String getPadding(FigmaFrameNode fNode) {
    String padding = "";
    padding += "paddingTop: '" + fNode.getPaddingTop() + "px',\n";
    padding += "paddingBottom: '" + fNode.getPaddingBottom() + "px',\n";
    padding += "paddingLeft: '" + fNode.getPaddingLeft() + "px',\n";
    padding += "paddingRight: '" + fNode.getPaddingRight() + "px',\n";

    return padding;
  }

  public String getChild(FigmaNode fNode, FigmaNode parentNode) {

    FigmaNodeTypes childType = fNode.getType();
    Map<String, TagData> tagDataMap = null;
    if (childType == FigmaNodeTypes.TEXT) {
      return textReactCGI.generate(fNode, parentNode, tagDataMap, null);
    } else if (FigmaNodeTypes.RECTANGLE == (childType)) {
      return rectangleReactCGI.generate(fNode, parentNode, tagDataMap, null);
    } else if (childType == (FigmaNodeTypes.FRAME)) {
      return generate(fNode, parentNode, tagDataMap, null);
    }
    return "";
  }

  // styling

  public String getHeight(FigmaFrameNode fNode) {
    if (fNode.getHeight() != 0) {
      return "height: '" + fNode.getHeight() + "px',\n";
    }
    return "height: '0px',\n";
  }

  public String getWidth(FigmaFrameNode fNode) {
    if (fNode.getWidth() != 0) {
      return "width: '" + fNode.getWidth() + "px',\n";
    }
    return "width: '0px',\n";
  }

  public String getBackgroundColour(FigmaFrameNode fNode) {
    final FillsSolid fills = (FillsSolid) fNode.getFills().get(0);
    final String begin = "backgroundColor: 'rgb(";
    final String end = ")',\n";
    final String fNodeColourR = (255 * fills.getColor().getR()) + ",";
    final String fNodeColourG = (255 * fills.getColor().getG()) + ",";
    final String fNodeColourB = String.valueOf(255 * fills.getColor().getB());
    return begin + fNodeColourR + fNodeColourG + fNodeColourB + end;
  }

  public String getBoxDecoration(FigmaFrameNode fNode) {
    final String upperBoxDecoration = "boxSizing: '";
    //        final String bottomBoxDecoration = ",\n";
    String genBoxDecoration = "";
    if (fNode.getCornerRadius() != 0) {
      genBoxDecoration = "border-box', \n borderRadius: '";
      genBoxDecoration = genBoxDecoration + borderRadius(fNode) + "px',\n";
    } else genBoxDecoration = "unset',\n";
    if (fNode.getStrokeWeight() != 1) {
      genBoxDecoration += border(fNode) + ",\n";
    }

    return upperBoxDecoration + genBoxDecoration;
  }

  public String borderRadius(FigmaFrameNode fNode) {

    return Float.toString(fNode.getCornerRadius());
  }

  public String border(FigmaFrameNode fNode) {
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

  public String getHorizontalPosition(FigmaFrameNode fNode) {
    return ("left: '" + (fNode.getX()) + "px',\n ");
  }

  public String getVerticalPosition(FigmaFrameNode fNode) {
    return ("top: '" + (fNode.getY()) + "px',\n ");
  }

  public String getVisibility(FigmaFrameNode fNode) {
    return ("visibility: " + fNode.isVisible() + ",\n");
  }

  public String getOpacity(FigmaFrameNode fNode) {
    return ("opacity: '" + (fNode.getOpacity()) + "',\n ");
  }

  @Override
  public Set<FigmaNodeMapper> getStrategy() {
    return Set.of(new FigmaNodeMapper(FigmaNodeTypes.FRAME, null));
  }
}
