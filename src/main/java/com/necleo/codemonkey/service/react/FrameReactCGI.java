package com.necleo.codemonkey.service.react;

import com.necleo.codemonkey.factory.ReactFigmaNodeAbstractFactory;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.figma.FigmaFrameNode;
import com.necleo.codemonkey.lib.types.figma.properties.fills.subtypes.FillsSolid;
import com.necleo.codemonkey.lib.utils.ReduceNumbersAfterDecimal;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FrameReactCGI implements ReactCGI {

  @Lazy ReactFigmaNodeAbstractFactory figmaNodeFactory;

  ReduceNumbersAfterDecimal reduceNumbersAfterDecimal;

  @Override
  public String generate(
      FigmaNode figmaNode,
      FigmaNode parentNode,
      Map<String, TagData> tagDataMap,
      Set<String> importsFunctions) {
    FigmaFrameNode fNode = (FigmaFrameNode) figmaNode;
    String genCode = "";

    genCode += "\n<div className='container' style={{ \n";
    //    if (parentNode != null) {
    //      genCode += getParentSpecialStyles((FigmaFrameNode) parentNode);
    //    }
    genCode += getStyles(figmaNode);
    genCode += " }}>";

    //    while(!fNode.getChild().equals(null)) {
    if (fNode.getChild() != null)
      for (FigmaNode childNode : fNode.getChild()) {
        genCode += getChild(childNode, figmaNode, tagDataMap, importsFunctions);
      }
    //    }
    genCode += "</div>\n";
    System.out.println(genCode); // end indent

    return genCode;
  }

  private String getParentSpecialStyles(FigmaFrameNode parentNode) {
    String parentStyles = "";
    if (parentNode.getLayoutMode().toString().equals("HORIZONTAL")) {
      parentStyles += "flexDirection: 'row',\n";
    } else if (parentNode.getLayoutMode().toString().equals("VERTICAL")) {
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
    if (fNode.getLayoutPositioning().equals("AUTO")) {
      styles += getAutoLayout(fNode);
    }
    styles += "gap: '" + fNode.getItemSpacing() + "px',\n";
    styles += getPadding(fNode);
    return styles;
  }

  public String getAutoLayout(FigmaFrameNode fNode) {
    String autoLS = "";
    //    autoLS += "width: full,\n";
    //    autoLS += "height: 100%,\n";
    autoLS += "display: 'flex',\n";

    if (fNode.getLayoutMode().toString().equals("HORIZONTAL")) {
      autoLS += "flexDirection: 'row',\n";
    } else if (fNode.getLayoutMode().toString().equals("VERTICAL")) {
      autoLS += "flexDirection: 'column',\n";
    }

    //    according to alignment fix justifyContent and position -
    //    left & top - fixed else absolute else no poz.
    //    justify we'll have to check for left right and center
    String primaryAlign = "";
    switch (fNode.getPrimaryAxisAlignItems().toString()) {
      case "MIN" -> primaryAlign = "flex-start";
      case "CENTER" -> primaryAlign = "center";
      case "MAX" -> primaryAlign = "flex-end";
      case "SPACE_BETWEEN" -> primaryAlign =
          fNode.getChild().size() > 1 ? "space-between" : "center";
    }
    autoLS += "justifyContent: '" + primaryAlign + "',\n";
    //    AlignItems -
    String counterAlign = "";
    switch (fNode.getCounterAxisAlignItems().toString()) {
      case "MIN" -> counterAlign = "flex-start";
      case "CENTER" -> counterAlign = "center";
      case "MAX" -> counterAlign = "flex-end";
    }
    autoLS += "alignItems: '" + counterAlign + "',\n";

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

  public String getChild(
      FigmaNode fNode,
      FigmaNode parentNode,
      Map<String, TagData> tagDataMap,
      Set<String> importsFunctions) {

    String genChild = "";

    Optional<ReactCGI> reactCGIOptional =
        figmaNodeFactory.getProcessor(FigmaNodeMapper.of(fNode, tagDataMap));
    genChild +=
        reactCGIOptional
            .map(reactCGI -> reactCGI.generate(fNode, parentNode, tagDataMap, importsFunctions))
            .orElseThrow();
    return genChild;
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
    if (CollectionUtils.isEmpty(fNode.getFills())) return "";
    else {
      String begin = "background: '";
      String fNodeColourR = null;
      String fNodeColourG = null;
      String fNodeColourB = null;
      String end = "), " + "#FA6650" + "',\n";
      String returnBackGround = "";
      FillsSolid fills = (FillsSolid) fNode.getFills().get(0);
      final String linearGrad =
          fills.getType().equals("SOLID") || fills.getType().equals("LINEAR")
              ? "linear-gradient(0deg, "
              : "";

      for (int i = 0; i < fNode.getFills().size(); i++) {
        if (fNode.getFills().size() == 1) {
          FillsSolid fillsInScope = (FillsSolid) fNode.getFills().get(0);
          fNodeColourR =
              reduceNumbersAfterDecimal.reducerDecimal(fillsInScope.getColor().getR()) + ",";
          fNodeColourG =
              reduceNumbersAfterDecimal.reducerDecimal(fillsInScope.getColor().getG()) + ",";
          fNodeColourB = reduceNumbersAfterDecimal.reducerDecimal(fillsInScope.getColor().getB());
          return "backgroundColor: 'rgb(" + fNodeColourR + fNodeColourG + fNodeColourB + ")',\n";
        }
        FillsSolid fillsInScope = (FillsSolid) fNode.getFills().get(i);
        fNodeColourR =
            reduceNumbersAfterDecimal.reducerDecimal(fillsInScope.getColor().getR()) + ",";
        fNodeColourG =
            reduceNumbersAfterDecimal.reducerDecimal(fillsInScope.getColor().getG()) + ",";
        fNodeColourB =
            reduceNumbersAfterDecimal.reducerDecimal(fillsInScope.getColor().getB()) + ",";
        returnBackGround +=
            "rgba("
                + fNodeColourR
                + fNodeColourG
                + fNodeColourB
                + (new DecimalFormat("#.#")).format(fillsInScope.getOpacity())
                + "),";
        if (i + 1 == fNode.getFills().size()) {
          int lastCommaIndex = returnBackGround.lastIndexOf(",");
          returnBackGround =
              returnBackGround.substring(0, lastCommaIndex)
                  + returnBackGround.substring(lastCommaIndex + 1);
        }
      }
      String str = begin + linearGrad + returnBackGround + end;
      //      int lastCommaIndex = str.lastIndexOf(",")-2;
      //      return str.substring(0, lastCommaIndex) + str.substring(lastCommaIndex + 1);
      return str;
    }
  }

  public String getBoxDecoration(FigmaFrameNode fNode) {
    final String upperBoxDecoration = "boxSizing: '";
    //        final String bottomBoxDecoration = ",\n";
    String genBoxDecoration;
    if (fNode.getCornerRadius() != 0) {
      genBoxDecoration = "border-box', \n borderRadius: '";
      genBoxDecoration = genBoxDecoration + borderRadius(fNode) + "px',\n";
    } else genBoxDecoration = "unset',\n";

    if (!fNode.getStrokes().isEmpty()) {
      genBoxDecoration += border(fNode) + ",\n";
    }

    return upperBoxDecoration + genBoxDecoration;
  }

  public String borderRadius(FigmaFrameNode fNode) {

    return Float.toString(fNode.getCornerRadius());
  }

  public String border(FigmaFrameNode fNode) {
    final String fNodeColourR =
        reduceNumbersAfterDecimal.reducerDecimal(fNode.getStrokes().get(0).getColor().getR()) + ",";
    final String fNodeColourG =
        reduceNumbersAfterDecimal.reducerDecimal(fNode.getStrokes().get(0).getColor().getG()) + ",";
    final String fNodeColourB =
        reduceNumbersAfterDecimal.reducerDecimal(fNode.getStrokes().get(0).getColor().getB());

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

  private String getRotation(FigmaFrameNode fNode) {
    return ("transform: 'rotate(-" + fNode.getRotation() + "deg)',\n");
  }

  @Override
  public Set<FigmaNodeMapper> getStrategy() {
    return Set.of(new FigmaNodeMapper(FigmaNodeTypes.FRAME, null));
  }
}
