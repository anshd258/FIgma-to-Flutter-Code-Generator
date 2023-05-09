package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.figma.FigmaLineNode;
import com.necleo.codemonkey.lib.types.figma.properties.strokes.Color;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import com.necleo.codemonkey.model.factory.NecleoDataNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
@Slf4j
public class LineFlutterCGI implements FlutterCGI {
  SizeUtil sizeUtil = new SizeUtil();

  @Override
  public Set<FigmaNodeMapper> getStrategy() {
    return Set.of(new FigmaNodeMapper(FigmaNodeTypes.LINE, null));
  }

  @Override
  public String generate(NecleoDataNode necleoDataNode) {
    if (!(necleoDataNode.fNode instanceof FigmaLineNode fNode)) {
      throw new IllegalArgumentException();
    }
    return generat(fNode);
  }

  private String generat(FigmaLineNode fNode) {
    String genCode = "";
    if (fNode.getDashPattern() != null) {
      //            genCode += getCotumPainter(fNode);
      //           genCode += getWidget(fNode);
    } else {
      genCode += getDivider(fNode);
    }

    return genCode;
  }

  private String getDivider(FigmaLineNode fNode) {
    final String upperContainer = "Container(\n\t";
    final String lowerContainer = "),\n";
    String genCode = sizeUtil.getWidth(fNode);
    genCode += "height:" + fNode.getStrokeWeight() + ",\n";
    genCode += getBoxDecoration(fNode);
    return upperContainer + genCode + lowerContainer;
  }

  private String getBoxDecoration(FigmaLineNode fNode) {
    final String upperBoxDecoration = "decoration: BoxDecoration(\n";
    final String bottomBoxDecoration = "),\n";
    String genBoxDecoration = "";

    if (!(fNode.getStrokes().isEmpty())) {

      genBoxDecoration +=
          "color:" + color(fNode.getStrokes().get(0).getColor(), fNode.getOpacity()) + ",\n";
    }
    System.out.println(fNode.getFills().get(0).getType());

    if (fNode.getStrokeCap().equals("ROUND")) {
      genBoxDecoration += borderRadius(fNode);
    }

    return upperBoxDecoration + genBoxDecoration + bottomBoxDecoration;
  }

  private String borderRadius(FigmaLineNode fNode) {
    final String upperBorderRadius = " borderRadius: BorderRadius.all(";
    final String bottomBorderRadius = "),\n";
    return upperBorderRadius + (fNode.getStrokeWeight() / 2) + bottomBorderRadius;
  }

  private String color(Color color, float opac) {
    final String upperColor = " Color.fromRGBO(\n";
    final String lowerColor = ")\n";
    final String red = Math.round(color.getR() * 255) + ",";
    final String green = Math.round(color.getG() * 255) + ",";
    final String blue = Math.round(color.getB() * 255) + ",";
    final String opacity = Double.toString(opac);

    return upperColor + red + green + blue + opacity + lowerColor;
  }

  //    private String getWidget(FigmaLineNode fNode) {
  //    }
  //
  //    private String getCotumPainter(FigmaLineNode fNode) {
  //    }
}
