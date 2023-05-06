package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.TagDataType;
import com.necleo.codemonkey.lib.types.figma.FigmaRectangleNode;
import com.necleo.codemonkey.lib.types.figma.properties.fills.subtypes.FillsSolid;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import com.necleo.codemonkey.model.factory.NecleoDataNode;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CheckBoxTagFlutterCGI implements FlutterCGI {
  @Override
  public Set<FigmaNodeMapper> getStrategy() {
    return Set.of(new FigmaNodeMapper(FigmaNodeTypes.RECTANGLE, TagDataType.CHECKBOX));
  }

  @Override
  public String generate(NecleoDataNode necleoDataNode) {

    if (!(necleoDataNode.fNode instanceof FigmaRectangleNode fNode)) {
      throw new IllegalArgumentException();
    }
    return generat(fNode);
  }

  private String generat(FigmaRectangleNode fNode) {
    final String upperTheme =
        "Theme(\n"
            + "      data: ThemeData(\n"
            + "        unselectedWidgetColor:\n"
            + "            Colors.grey, // change color of unchecked checkbox\n";
    final String lowerTheme = ");\n";
    String genCode = "";
    if (fNode.getFills().get(0).getType().equals("SOLID")) {
      final FillsSolid fills = (FillsSolid) fNode.getFills().get(0);
      if (fills.getColor() != null && fNode.getFills().get(0).getOpacity() != 0) {
        genCode += color(fills);
      }
    }
    genCode += getScale(fNode);
    return upperTheme + genCode + lowerTheme;
  }

  private String getScale(FigmaRectangleNode fNode) {
    final String upperTransform = " child: Transform.scale(\n" + "        scale:";
    final String lowerTransform = " ),\n";
    String genCode = "";
    genCode += getScaleFactor(fNode);
    genCode += getCheckBox();
    return upperTransform + genCode + lowerTransform;
  }

  private String getCheckBox() {
    final String upperCheckBox = "child: Checkbox(\n" + "          value: _isChecked,";
    final String lowerCheckBox =
        " onChanged: (newValue) {\n"
            + "            setState(() {\n"
            + "              _isChecked = newValue!;\n"
            + "            });\n"
            + "          },\n"
            + "        ),\n";
    return upperCheckBox + lowerCheckBox;
  }

  private String getScaleFactor(FigmaRectangleNode fNode) {
    final String upperMin = "min(";
    final String lowerMin = "),\n";
    String genCode = fNode.getHeight() + "/24," + fNode.getWidth() + "/24";
    return upperMin + genCode + lowerMin;
  }

  private String color(FillsSolid fills) {
    final String upperColor = "accentColor: Color.fromRGBO(\n";
    final String lowerColor = "),),\n";
    final String red = Math.round(fills.getColor().getR() * 255) + ",";
    final String green = Math.round(fills.getColor().getG() * 255) + ",";
    final String blue = Math.round(fills.getColor().getB() * 255) + ",";
    final String opacity = Float.toString(fills.getOpacity());

    return upperColor + red + green + blue + opacity + lowerColor;
  }
}
