package com.necleo.codemonkey.service.flutter;

import static com.necleo.codemonkey.constant.MDCKey.X_PROJECT_ID;
import static com.necleo.codemonkey.lib.types.figma.properties.fills.enums.ScaleMode.FILL;

import com.necleo.codemonkey.configuration.S3FileLoader;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.figma.FigmaRectangleNode;
import com.necleo.codemonkey.lib.types.figma.properties.fills.Color;
import com.necleo.codemonkey.lib.types.figma.properties.fills.subtypes.FillsGradient;
import com.necleo.codemonkey.lib.types.figma.properties.fills.subtypes.FillsImage;
import com.necleo.codemonkey.lib.types.figma.properties.fills.subtypes.FillsSolid;
import com.necleo.codemonkey.lib.types.figma.properties.strokes.Strokes;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import java.net.URL;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RectangleFlutterCGI implements FlutterCGI {

//  S3FileLoader s3FileLoader;
  SizeUtil sizeUtil = new SizeUtil();

  @Override
  public Set<FigmaNodeMapper> getStrategy() {
    return Set.of(new FigmaNodeMapper(FigmaNodeTypes.RECTANGLE, null));
  }

  @Override
  public String generate(FigmaNode figmaNode, TagData tagData) {
    if (!(figmaNode instanceof FigmaRectangleNode fNode)) {
      throw new IllegalArgumentException();
    }
    return generat(fNode);
  }

  public String generat(FigmaRectangleNode figmaNode) {

    String genCode = "";

    genCode += "\nContainer( \n";
    genCode += sizeUtil.getHeight(figmaNode);
    genCode += sizeUtil.getWidth(figmaNode);
    genCode += getBoxDecoration(figmaNode);

    genCode += "),\n";
    // end indent

    return genCode;
  }

  //  private String getHeight(FigmaRectangleNode fNode) {
  //    if (fNode.getHeight() != 0) {
  //      return "height:" + Integer.toString(fNode.getHeight()) + ",\n";
  //    }
  //    return "height:0,\n";
  //  }
  //
  //  private String getWidth(FigmaRectangleNode fNode) {
  //    if (fNode.getWidth() != 0) {
  //      return "width:" + Integer.toString(fNode.getWidth()) + ",\n";
  //    }
  //    return "width:0,\n";
  //  }

  private String getBoxDecoration(FigmaRectangleNode fNode) {
    final String upperBoxDecoration = "decoration: BoxDecoration(\n";
    final String bottomBoxDecoration = "),\n";
    String genBoxDecoration = "";

    if (fNode.getFills().get(0).getType().equals("SOLID")) {
      final FillsSolid fills = (FillsSolid) fNode.getFills().get(0);
      if (fills.getColor() != null && fNode.getFills().get(0).getOpacity() != 0) {
        genBoxDecoration += "color:" + color(fills) + ",\n";
      }
    }
    System.out.println(fNode.getFills().get(0).getType());
    if (fNode.getFills().get(0).getType().equals("IMAGE")) {
      final FillsImage fills = (FillsImage) fNode.getFills().get(0);

      genBoxDecoration += getImage(fills, fNode);
    }
    if (fNode.getFills().get(0).getType().equals("GRADIENT_LINEAR")) {
      final FillsGradient fills = (FillsGradient) fNode.getFills().get(0);
      genBoxDecoration += getGradient(fills);
    }

    if (fNode.getBottomLeftRadius() != 0
        || fNode.getTopLeftRadius() != 0
        || fNode.getTopRightRadius() != 0
        || fNode.getBottomRightRadius() != 0) {
      genBoxDecoration += borderRadius(fNode);
    }
    //    if (fNode.getStrokeWeight() != 0) {
    //      genBoxDecoration += border(fNode);
    //    }
    return upperBoxDecoration + genBoxDecoration + bottomBoxDecoration;
  }

  private String getGradient(FillsGradient fills) {
    final String upperLinearGradient = "gradient: LinearGradient(\n";
    final String lowerLinearGradient = " ),\n";
    String gencCode = "";
    gencCode +=
        "color:[\n"
            + fills.getGradientStops().stream()
                .map(gradientStops -> getGradientColor(gradientStops.getColor()))
            + "],\n";
    gencCode +=
        "  stops: [ \n"
            + fills.getGradientStops().stream().map(gradientStops -> gradientStops.getPosition())
            + "],\n";
    return upperLinearGradient + gencCode + lowerLinearGradient;
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

  private String getGradientColor(Color fills) {
    final String upperColor = " Color.fromRGBO(\n";
    final String lowerColor = ")\n";
    final String red = Math.round(fills.getR() * 255) + ",";
    final String green = Math.round(fills.getG() * 255) + ",";
    final String blue = Math.round(fills.getB() * 255) + ",";
    final String opacity = Double.toString(fills.getA());

    return upperColor + red + green + blue + opacity + lowerColor;
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

  private String getImage(FillsImage fills, FigmaRectangleNode figmaRectangleNode) {
    final String upperImage = " image: DecorationImage(\n";
    final String lowerImage = "),\n";
    String genImage = "";
    genImage += getNetworkImage(fills, figmaRectangleNode);
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

  private String getNetworkImage(FillsImage fills, FigmaRectangleNode figmaRectangleNode) {
    String imageHash = fills.getImageHash();
    String projectId = MDC.get(X_PROJECT_ID);

//    URL s3ImageUrl = s3FileLoader.getImageUrl(imageHash, projectId);
    final String upperImage = " image: NetworkImage(\n";
    final String lowerImage = "),\n";
    final String imageUrl = "'" + " " + "'";
    return upperImage + imageUrl + lowerImage;
  }

  private String color(FillsSolid fills) {
    final String upperColor = "Color.fromRGBO(\n";
    final String lowerColor = ")\n";
    final String red = Math.round(fills.getColor().getR() * 255) + ",";
    final String green = Math.round(fills.getColor().getG() * 255) + ",";
    final String blue = Math.round(fills.getColor().getB() * 255) + ",";
    final String opacity = Float.toString(fills.getOpacity());

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

  private String border(FigmaRectangleNode fNode) {
    final String upperBorder = " border: Border.all(";
    final String bottomBorder = "),\n";
    String genCode = "";
    genCode += getStrokeAlignment(fNode);
    genCode += getColor(fNode.getStrokes().get(0));
    genCode += getStrokeWidth(fNode);

    genCode += getStyle(fNode.getStrokes().get(0));
    return upperBorder + genCode + bottomBorder;
  }
}
