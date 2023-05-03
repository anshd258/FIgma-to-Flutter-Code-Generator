package com.necleo.codemonkey.service.react;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.figma.FigmaTextNode;
import com.necleo.codemonkey.lib.types.figma.properties.fills.subtypes.FillsSolid;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TextReactCGI implements ReactCGI {
  @Override
  public Set<FigmaNodeMapper> getStrategy() {

    return Set.of(new FigmaNodeMapper(FigmaNodeTypes.TEXT, null));
  }

  @Override
  public String generate(
          FigmaNode figmaNode, FigmaNode node, Map<String, TagData> tagDataMap, Set<String> importsFunctions) {
    if (!(figmaNode instanceof FigmaTextNode fNode)) {
      throw new IllegalArgumentException();
    }
    return generat(fNode);
  }

  //    public String generat(FigmaNode figmaNode) {
  //        FigmaTextNode fNode = (FigmaTextNode) figmaNode;
  //        String genCode = "";
  //    }

  public String generat(FigmaNode figmaNode) {
    FigmaTextNode fNode = (FigmaTextNode) figmaNode;
    String genCode = "";
    genCode += "\n<div style={{ \n";
    genCode += getStyle(fNode);
    genCode += " }}>";
    genCode += getData(fNode);
    genCode += "</div>\n";
    System.out.println(genCode); // end indent

    return genCode;
  }

  public String getStyle(FigmaTextNode fNode) {
    String style = "";
    final FillsSolid fills = (FillsSolid) fNode.getFills().get(0);
    // visible
    if (fNode.isVisible()) style += "visibility: true, \n";
    else style += "visibility: false, \n";
    // color
    style +=
        "color: 'rgb("
            + (fills.getColor().getR() * 255)
            + ", "
            + (fills.getColor().getG() * 255)
            + ", "
            + (fills.getColor().getB() * 255)
            + ")',\n";
    // align
    style += "left: '" + fNode.getX() + "px',\n";
    style += "top: '" + fNode.getY() + "px',\n";
    style += "opacity: '" + fNode.getOpacity() + "',\n";
    if (fNode.getStrokes().size() != 0) {
      style +=
          "border: '"
              + fNode.getStrokes().get(0).getType().toLowerCase()
              + ", "
              + fNode.getStrokeWeight()
              + "px, rgb("
              + fNode.getStrokes().get(0).getColor().getR() * 255
              + ", "
              + fNode.getStrokes().get(0).getColor().getG() * 255
              + ", "
              + fNode.getStrokes().get(0).getColor().getB() * 255
              + ")',\n";
    }
    style += getAlignment(fNode);
    style += getFont(fNode);
    style += getWidthHeight(fNode);
    style += "position: 'absolute',\n";

    return style;
  }

  private String getWidthHeight(FigmaTextNode fNode) {
    return "width: '" + fNode.getWidth() + "px',\n" + "height: '" + fNode.getHeight() + "px',\n" ;
  }

  public String getData(FigmaTextNode fNode) {
    //        if (!Objects.equals(fNode.getName(), ""))
    //            return " ";
    //        else
    return fNode.getName();
  }

  public String getFont(FigmaTextNode fNode) {
    String font = "";
    font += "fontWeight: '" + fNode.getFontWeight() + "',\n ";
    font += "fontFace: '" + fNode.getFontFamily() + "',\n";
    font += "fontSize: '" + fNode.getFontSize() + "',\n";

    return font;
  }

  public String getAlignment(FigmaTextNode fNode) {
    String alignment = "";

    //       alignment += "justifyContent: '"+ (fNode.getPrimaryAxisAlignitems() ?
    // fNode.getPrimaryAxisAlignitems().toString().toLowerCase()  :'' )  +"',\n";
    alignment += "lineHeight: '" + "auto" + "',\n";
    return alignment;
  }
}
