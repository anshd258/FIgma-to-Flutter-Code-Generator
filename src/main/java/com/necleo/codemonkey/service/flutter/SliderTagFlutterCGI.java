package com.necleo.codemonkey.service.flutter;

import static com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes.RECTANGLE;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.TagDataType;
import com.necleo.codemonkey.lib.types.figma.FigmaFrameNode;
import com.necleo.codemonkey.lib.types.figma.FigmaRectangleNode;
import com.necleo.codemonkey.lib.types.figma.properties.fills.subtypes.FillsSolid;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SliderTagFlutterCGI implements FlutterCGI {
  SizeUtil sizeUtil = new SizeUtil();

  @Override
  public Set<FigmaNodeMapper> getStrategy() {
    return Set.of(new FigmaNodeMapper(FigmaNodeTypes.FRAME, TagDataType.SLIDER));
  }

  @Override
  public String generate(FigmaNode figmaNode, TagData tagData) {
    if (!(figmaNode instanceof FigmaFrameNode fNode)) {
      throw new IllegalArgumentException();
    }
    return generat(fNode, tagData);
  }

  private String generat(FigmaFrameNode fNode, TagData tagData) {
    final String upperBoxing = "SizedBox(\n";
    final String lowerBoxing = ");";
    String genCode = "";
    if (fNode.getChild().get(0).getType() == RECTANGLE) {
      if (!(fNode.getChild().get(0) instanceof FigmaRectangleNode fNode1)) {
        throw new IllegalArgumentException();
      }
      genCode += getSize(fNode1);
    }

    genCode += getSliderTheam(fNode);

    return getFunction(tagData) + upperBoxing + genCode + lowerBoxing;
  }

  private String getSliderTheam(FigmaFrameNode fNode) {
    final String upperSliderTheme = " child: SliderTheme(\n";

    final String lowerSliderTheme = "),\n";
    String genCode = "";
    genCode += getData(fNode);
    genCode += getSlider(fNode);
    return upperSliderTheme + genCode + lowerSliderTheme;
  }

  private String getData(FigmaFrameNode fNode) {
    final String upperSliderData = "  data: SliderThemeData(\n";
    final String lowerSliderData = " ),\n";
    String genCode = "";
    if (fNode.getChild().get(0).getType() == RECTANGLE) {
      if (!(fNode.getChild().get(0) instanceof FigmaRectangleNode fNode1)) {
        throw new IllegalArgumentException();
      }

      genCode += getThumbShape(fNode1);
      genCode += getThumbHeight(fNode1);
      genCode += getActiveTrackColor(fNode1);
      genCode += getInActiveTrackColor(fNode1);
    }
    if (fNode.getChild().get(1).getType() == RECTANGLE) {
      if (!(fNode.getChild().get(1) instanceof FigmaRectangleNode fNode2)) {
        throw new IllegalArgumentException();
      }
      genCode += getThumbColor(fNode2);
    }

    genCode += "overlayColor: Colors.transparent,\n";

    return upperSliderData + genCode + lowerSliderData;
  }

  private String getInActiveTrackColor(FigmaRectangleNode fNode) {
    String inaActiveTrackColor = "";
    if (fNode.getFills().get(0).getType().equals("SOLID")) {
      final FillsSolid fills = (FillsSolid) fNode.getFills().get(0);
      if (fills.getColor() != null && fNode.getFills().get(0).getOpacity() != 0) {
        inaActiveTrackColor += color(fills);
      }
    }
    return "inactiveTrackColor:" + inaActiveTrackColor + ".withOpacity(0.3),\n";
  }

  private String getActiveTrackColor(FigmaRectangleNode fNode) {
    String activeTrackColor = "";
    if (fNode.getFills().get(0).getType().equals("SOLID")) {
      final FillsSolid fills = (FillsSolid) fNode.getFills().get(0);
      if (fills.getColor() != null && fNode.getFills().get(0).getOpacity() != 0) {
        activeTrackColor += color(fills);
      }
    }
    return "activeTrackColor: " + activeTrackColor + ",\n";
  }

  private String getThumbHeight(FigmaRectangleNode fNode) {
    return "trackHeight:" + fNode.getHeight() + ",\n";
  }

  private String getThumbShape(FigmaRectangleNode fNode) {
    final String upperThumbShape = " thumbShape: RoundSliderThumbShape(enabledThumbRadius:";
    final String lowerThumbShape = "/ 2.5),\n";
    return upperThumbShape + fNode.getHeight() + lowerThumbShape;
  }

  private String getThumbColor(FigmaRectangleNode fNode) {
    String genColor = "";
    if (fNode.getFills().get(0).getType().equals("SOLID")) {
      final FillsSolid fills = (FillsSolid) fNode.getFills().get(0);
      if (fills.getColor() != null && fNode.getFills().get(0).getOpacity() != 0) {
        genColor += color(fills);
      }
    }
    return "thumbColor:" + genColor + ",\n";
  }

  private String color(FillsSolid fills) {
    final String upperColor = " Color.fromRGBO(\n";
    final String lowerColor = ")";
    final String red = Math.round(fills.getColor().getR() * 255) + ",";
    final String green = Math.round(fills.getColor().getG() * 255) + ",";
    final String blue = Math.round(fills.getColor().getB() * 255) + ",";
    final String opacity = Float.toString(fills.getOpacity());

    return upperColor + red + green + blue + opacity + lowerColor;
  }

  private String getSlider(FigmaFrameNode fNode) {
    final String upperSlider =
        "child : Slider(\n"
            + "            value: _value,\n"
            + "            min: 0,\n"
            + "            max: 100,\n"
            + "            onChanged: func";
    final String lowerSlider = " ),\n";
    String genCode = "";

    return upperSlider + genCode + lowerSlider;
  }

  private String getFunction(TagData tagData) {
    final String upperFunction =
        "void func(newValue) {\n"
            + "    setState(() {\n"
            + "      _value = newValue;\n"
            + "    });";
    final String lowerFunction = "}\n\n";
    String genCode = "";

    return upperFunction + genCode + lowerFunction;
  }

  private String getSize(FigmaRectangleNode fNode) {
    String genSize = "";
    genSize += sizeUtil.getWidth(fNode);

    genSize += sizeUtil.getHeight(fNode);
    return genSize;
  }
}
