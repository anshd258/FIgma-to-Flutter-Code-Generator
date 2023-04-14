package com.necleo.codemonkey.service.flutter;

import static com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes.RECTANGLE;
import static com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes.TEXT;
import static com.necleo.codemonkey.lib.types.figma.properties.fills.enums.ScaleMode.FILL;

import com.necleo.codemonkey.factory.mapper.FigmaNodeMapper;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.figma.FigmaFrameNode;
import com.necleo.codemonkey.lib.types.figma.properties.fills.subtypes.FillsImage;
import com.necleo.codemonkey.lib.types.figma.properties.fills.subtypes.FillsSolid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FrameFlutterCGI implements FlutterCGI {
  RectangleFlutterCGI rectangleFlutterCGI = new RectangleFlutterCGI();
  TextFlutterCGI textFlutterCGI = new TextFlutterCGI();

  @Override
  public FigmaNodeMapper getEnumMapping() {
    return new FigmaNodeMapper(FigmaNodeTypes.FRAME,null);
  }

  @Override
  public String generate(FigmaNode figmaNode) {
    if (!(figmaNode instanceof FigmaFrameNode fNode)) {
      throw new IllegalArgumentException();
    }
    return generat(fNode);
  }

  private String generat(FigmaFrameNode figmaNode) {
    String genCode = "";

    genCode += "\nContainer( \n";
    genCode += getHeight(figmaNode);
    genCode += getWidth(figmaNode);
    genCode += getBoxDecoration(figmaNode);
    if (figmaNode.getChild().size() > 1) {
      genCode += getchild(figmaNode);
    }

    genCode += getchild(figmaNode);

    genCode += ")\n";
    System.out.println(genCode); // end indent

    return genCode;
  }

  private String getchild(FigmaNode figmaNode) {
    if (figmaNode.getChild().get(0).getType() == RECTANGLE)
      return "child:" + rectangleFlutterCGI.generate(figmaNode.getChild().get(0)) + ",\n";
    else if (figmaNode.getChild().get(0).getType() == TEXT) {
      return "child:" + textFlutterCGI.generate(figmaNode.getChild().get(0)) + ",\n";
    }
    return "";
  }

  private String getHeight(FigmaFrameNode fNode) {
    if (fNode.getHeight() != 0) {
      return "height:" + Integer.toString(fNode.getHeight()) + ",\n";
    }
    return "height:0,\n";
  }

  private String getWidth(FigmaFrameNode fNode) {
    if (fNode.getWidth() != 0) {
      return "width:" + Integer.toString(fNode.getWidth()) + ",\n";
    }
    return "width:0,\n";
  }

  private String getBoxDecoration(FigmaFrameNode fNode) {
    final String upperBoxDecoration = "decoration: BoxDecoration(\n";
    final String bottomBoxDecoration = "),\n";
    String genBoxDecoration = "";

    if (fNode.getFills().get(0).getType().equals("SOLID")) {
      final FillsSolid fills = (FillsSolid) fNode.getFills().get(0);
      if (fills.getColor() != null && fNode.getFills().get(0).getOpacity() != 0) {
        genBoxDecoration += color(fills);
      }
    }
    System.out.println(fNode.getFills().get(0).getType());
    if (fNode.getFills().get(0).getType().equals("IMAGE")) {
      final FillsImage fills = (FillsImage) fNode.getFills().get(0);

      genBoxDecoration += getImage(fills);
    }

    if (fNode.getBottomLeftRadius() != 0
        || fNode.getTopLeftRadius() != 0
        || fNode.getTopRightRadius() != 0
        || fNode.getBottomRightRadius() != 0) {
      genBoxDecoration += borderRadius(fNode);
    }
    if (fNode.getStrokeWeight() != 0) {
      genBoxDecoration += border(fNode);
    }
    return upperBoxDecoration + genBoxDecoration + bottomBoxDecoration;
  }

  private String getImage(FillsImage fills) {
    final String upperImage = " image: DecorationImage(\n";
    final String lowerImage = "),\n";
    String genImage = "";
    genImage += getNetworkImage(fills);
    genImage += getFit(fills);
    return upperImage + genImage + lowerImage;
  }

  private String getFit(FillsImage fills) {
    String filltype = "";
    if (fills.getScaleMode().equals(FILL)) {
      filltype = "fill";
    }
    return "fit: BoxFit." + filltype + ",\n";
  }

  private String getNetworkImage(FillsImage fills) {
    final String upperImage = " image: NetworkImage(\n";
    final String lowerImage = "),\n";
    final String imageUrl = "'" + fills.getImageHash() + "'";
    return upperImage + imageUrl + lowerImage;
  }

  private String color(FillsSolid fills) {
    final String upperColor = "color: Color.fromRGBO(\n";
    final String lowerColor = "),\n";
    final String red = Math.round(fills.getColor().getR() * 255) + ",";
    final String green = Math.round(fills.getColor().getG() * 255) + ",";
    final String blue = Math.round(fills.getColor().getB() * 255) + ",";
    final String opacity = Float.toString(fills.getOpacity());

    return upperColor + red + green + blue + opacity + lowerColor;
  }

  private String borderRadius(FigmaFrameNode fNode) {
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

  private String border(FigmaFrameNode fNode) {
    final String upperBorder = " border: Border.all(";
    final String bottomBorder = "),\n";
    final String width = "width:" + Float.toString(fNode.getStrokeWeight()) + ",\n";
    return upperBorder + width + bottomBorder;
  }
}
