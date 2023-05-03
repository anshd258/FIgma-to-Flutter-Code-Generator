package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.TagDataType;
import com.necleo.codemonkey.lib.types.figma.FigmaRectangleNode;
import com.necleo.codemonkey.lib.types.figma.properties.strokes.Strokes;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InputTagFlutterCGI implements FlutterCGI {
  SizeUtil sizeUtil = new SizeUtil();

  @Override
  public Set<FigmaNodeMapper> getStrategy() {
    return Set.of(new FigmaNodeMapper(FigmaNodeTypes.RECTANGLE, TagDataType.INPUT));
  }

  @Override
  public String generate(FigmaNode figmaNode, TagData tagData) {

    if (!(figmaNode instanceof FigmaRectangleNode fNode)) {
      throw new IllegalArgumentException();
    }
    return generat(fNode);
  }

  private String generat(FigmaRectangleNode fNode) {
    final String upperSizedBox = "SizedBox(\n";
    final String lowerSizedBox = ")\n";
    String genCode = "";
    genCode += getSize(fNode);
    genCode += genTextField(fNode);
    return upperSizedBox + genCode + lowerSizedBox;
  }

  private String genTextField(FigmaRectangleNode fNode) {
    final String upperTextField = " child: TextField(\n" + "    decoration:";
    final String lowerTextField = "),\n";
    String genDecoration = "";
    genDecoration += getDecoration(fNode);
    return upperTextField + genDecoration + lowerTextField;
  }

  private String getDecoration(FigmaRectangleNode fNode) {
    final String upperInputDecoration = "InputDecoration(\n";
    final String lowerInputDecoration = "),\n";
    String genInputDecoration = "";
    genInputDecoration += getBorder(fNode);
    return upperInputDecoration + genInputDecoration + lowerInputDecoration;
  }

  private String getBorder(FigmaRectangleNode fNode) {
    String enabledBorder = "enabledBorder:";
    String focusBorder = "focusedBorder:";
    enabledBorder += getOutlinedBorder(fNode);
    focusBorder += getOutlinedBorder(fNode);
    return enabledBorder + focusBorder;
  }

  private String getOutlinedBorder(FigmaRectangleNode fNode) {
    final String upperBorder = "OutlineInputBorder(\n";
    final String lowerBorder = "),\n";
    String genBorder = "";
    genBorder += borderRadius(fNode);
    genBorder += border(fNode);
    return upperBorder + genBorder + lowerBorder;
  }

  private String border(FigmaRectangleNode fNode) {
    final String upperBorderSide = "borderSide: BorderSide(\n";
    final String lowerBorderSide = "),\n";

    String genBorder = "";
    genBorder += getColor(fNode.getStrokes().get(0));
    genBorder += getStrokeWidth(fNode);
    genBorder += getStrokeAlignment(fNode);
    genBorder += getStyle(fNode.getStrokes().get(0));
    return upperBorderSide + genBorder + lowerBorderSide;
  }

  private String getStyle(Strokes strokes) {
    return "style: BorderStyle." + strokes.getType().toString().toLowerCase() + ",\n";
  }

  private String getStrokeAlignment(FigmaRectangleNode fNode) {
    return " strokeAlign: StrokeAlign." + fNode.getStrokeAlign().toString().toLowerCase() + ",\n";
  }

  private String getStrokeWidth(FigmaRectangleNode fNode) {
    return "width:" + fNode.getStrokeWeight() + ",\n";
  }

  private String getColor(Strokes fills) {
    final String upperColor = "color: Color.fromRGBO(\n";
    final String lowerColor = "),\n";
    final String red = Math.round(fills.getColor().getR() * 255) + ",";
    final String green = Math.round(fills.getColor().getG() * 255) + ",";
    final String blue = Math.round(fills.getColor().getB() * 255) + ",";
    final String opacity = Double.toString(fills.getOpacity());

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

  private String getSize(FigmaRectangleNode fNode) {
    String genSize = "";
    genSize += sizeUtil.getHeight(fNode);
    genSize += sizeUtil.getWidth(fNode);
    return genSize;
  }
}
