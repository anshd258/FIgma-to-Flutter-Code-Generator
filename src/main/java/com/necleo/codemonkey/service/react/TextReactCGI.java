package com.necleo.codemonkey.service.react;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.TagDataType;
import com.necleo.codemonkey.lib.types.figma.FigmaTextNode;
import com.necleo.codemonkey.lib.types.figma.properties.fills.subtypes.FillsSolid;
import com.necleo.codemonkey.lib.utils.ReduceNumbersAfterDecimal;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TextReactCGI implements ReactCGI {
  ReduceNumbersAfterDecimal reduceNumbersAfterDecimal;

  @Override
  public Set<FigmaNodeMapper> getStrategy() {

    return Set.of(
        new FigmaNodeMapper(FigmaNodeTypes.TEXT, null),
        new FigmaNodeMapper(FigmaNodeTypes.TEXT, TagDataType.TEXT));
  }

  @Override
  public String generate(
      FigmaNode figmaNode,
      FigmaNode node,
      Map<String, TagData> tagDataMap,
      Set<String> importsFunctions) {
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
              + reduceNumbersAfterDecimal.reducerDecimal(
                  fNode.getStrokes().get(0).getColor().getR())
              + ", "
              + reduceNumbersAfterDecimal.reducerDecimal(
                  fNode.getStrokes().get(0).getColor().getG())
              + ", "
              + reduceNumbersAfterDecimal.reducerDecimal(
                  fNode.getStrokes().get(0).getColor().getB())
              + ")',\n";
    }
    style += getAlignment(fNode);
    style += getFont(fNode);
    style += getWidthHeight(fNode);
    //    style += "position: 'absolute',\n";
    style +=
        "letterSpacing: '"
            + (new DecimalFormat("#.#")).format(fNode.getLetterSpacing().getValue())
            + "px',\n";

    return style;
  }

  private String getWidthHeight(FigmaTextNode fNode) {
    return "width: '" + fNode.getWidth() + "px',\n" + "height: '" + fNode.getHeight() + "px',\n";
  }

  public String getData(FigmaTextNode fNode) {
    //        if (!Objects.equals(fNode.getName(), ""))
    //            return " ";
    //        else
    return fNode.getCharacters();
  }

  public String getFont(FigmaTextNode fNode) {
    String font = "";
    font += "fontWeight: '" + fNode.getFontWeight() + "',\n ";
    //    if (!(fNode.getFontName() instanceof Object))
    //      font += "fontFace: 'Sans-Serif',\n";
    //    else
    font += "fontFace: '" + fNode.getFontName().getFamily() + "',\n";
    font += "fontSize: '" + fNode.getFontSize() + "px',\n";

    return font;
  }

  public String getAlignment(FigmaTextNode fNode) {
    String alignment = "";

    //       alignment += "justifyContent: '"+ (fNode.getPrimaryAxisAlignitems() ?
    // fNode.getPrimaryAxisAlignitems().toString().toLowerCase()  :'' )  +"',\n";
    String lineHeightUnit = fNode.getLineHeight().getUnit().equals("PIXELS") ? "px" : "rem";
    alignment += "lineHeight: '" + fNode.getLineHeight().getValue() + lineHeightUnit + "',\n";
    alignment += "display: 'flex',\n";
    alignment += "justifyContent: 'center',\n";
    return alignment;
  }
}
