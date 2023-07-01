package com.necleo.codemonkey.service.flutter;

import static com.necleo.codemonkey.lib.types.figma.properties.fills.enums.ScaleMode.FILL;

import com.necleo.codemonkey.flutter.index.FlutterGI;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.LayoutMode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.figma.FigmaFrameNode;
import com.necleo.codemonkey.lib.types.figma.properties.fills.Color;
import com.necleo.codemonkey.lib.types.figma.properties.fills.subtypes.FillsGradient;
import com.necleo.codemonkey.lib.types.figma.properties.fills.subtypes.FillsImage;
import com.necleo.codemonkey.lib.types.figma.properties.fills.subtypes.FillsSolid;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import com.necleo.codemonkey.model.factory.FlutterWI;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FrameFlutterCGI implements FlutterCGI {

  AutoLayoutFrameCGI autoLayoutFrameCGI;

  @Override
  public Set<FigmaNodeMapper> getStrategy() {
    return Set.of(new FigmaNodeMapper(FigmaNodeTypes.FRAME, null));
  }

  @Override
  public String generate(
      FigmaNode figmaNode, FigmaNode parentFigmaNode, FlutterGI flutterGI, FlutterWI flutterWI) {
    if (!(figmaNode instanceof FigmaFrameNode fNode)) {
      throw new IllegalArgumentException();
    }

    StringBuilder genCode = new StringBuilder();
    String startContainer =
        """
            Container(
              %s
              %s
            """
            .formatted(getPadding(fNode), getBoxDecoration(fNode));

    genCode.append(startContainer);
    return autoLayoutFrameCGI.generateAutoLayoutFrame(
        genCode, fNode, flutterWI, flutterGI, parentFigmaNode);
  }

  @Override
  public String generate(FlutterWI flutterNecleoDataNode, FigmaNode figmaNode) {
    return null;
  }

  private String getPadding(FigmaFrameNode figmaNode) {
    if (!figmaNode.getLayoutMode().equals(LayoutMode.NONE)) {
      if (figmaNode.getPaddingRight() == figmaNode.getPaddingLeft()
          && figmaNode.getPaddingTop() == figmaNode.getPaddingBottom()
          && figmaNode.getPaddingRight() == figmaNode.getPaddingTop()) {
        return """
            padding: EdgeInsets.all(%s),
              """
            .formatted(figmaNode.getPaddingRight());
      }
      if (figmaNode.getPaddingRight() == figmaNode.getPaddingLeft()
          && figmaNode.getPaddingTop() == figmaNode.getPaddingBottom()) {
        return """
            padding: EdgeInsets.symmetric(vertical: %s, horizontal: %s),
              """
            .formatted(figmaNode.getPaddingTop(), figmaNode.getPaddingRight());
      }

      return """
            padding: EdgeInsets.only(
              left: %s,
              top: %s,
              right: %s,
              bottom: %s,
            ),
            """
          .formatted(
              figmaNode.getPaddingLeft(),
              figmaNode.getPaddingTop(),
              figmaNode.getPaddingRight(),
              figmaNode.getPaddingBottom());
    }

    return "";

    //    final String upperPadding = " padding: EdgeInsets.only(\n";
    //    final String lowerPadding = "),\n";
    //    String bottom = "";
    //    String right = "";
    //    if (!(figmaNode.getChild().size() == 1
    //        && figmaNode.getChild().get(0).getType().equals(FigmaNodeTypes.TEXT))) {
    //      right = "right:" + figmaNode.getPaddingRight() + ",";
    //      bottom = "bottom:" + figmaNode.getPaddingBottom() + ",";
    //    }
    //    String leftPaing = "left:" + figmaNode.getPaddingLeft() + ",";
    //    String top = "top:" + figmaNode.getPaddingTop() + ",";
    //    return upperPadding + leftPaing + right + top + bottom + lowerPadding;
  }

  private String getBoxDecoration(FigmaFrameNode fNode) {
    if (!fNode.getFills().isEmpty()) {
      final String upperBoxDecoration = "decoration: BoxDecoration(\n";
      final String bottomBoxDecoration = "),\n";
      String genBoxDecoration = "";

      if (fNode.getFills().get(0).getType().equals("SOLID")) {
        final FillsSolid fills = (FillsSolid) fNode.getFills().get(0);
        if (fills.getColor() != null && fNode.getFills().get(0).getOpacity() != 0) {
          genBoxDecoration += "color:" + color(fills) + ",\n";
        }
      }

      if (fNode.getFills().get(0).getType().equals("IMAGE")) {
        final FillsImage fills = (FillsImage) fNode.getFills().get(0);

        genBoxDecoration += getImage(fills);
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
      if (fNode.getStrokeWeight() != 0) {
        genBoxDecoration += border(fNode);
      }
      return upperBoxDecoration + genBoxDecoration + bottomBoxDecoration;
    }
    return "";
  }

  private String getGradient(FillsGradient fills) {
    final String upperLinearGradient = "gradient: LinearGradient(\n";
    final String lowerLinearGradient = " ),\n";
    String gencCode = "";
    gencCode +=
        "colors:[\n"
            + fills.getGradientStops().stream()
                .map(gradientStops -> getGradientColor(gradientStops.getColor()) + ",\n")
                .collect(Collectors.joining())
            + "],\n";
    gencCode +=
        "  stops: [ \n"
            + fills.getGradientStops().stream()
                .map(gradientStops -> gradientStops.getPosition() + ",")
                .collect(Collectors.joining())
            + "],\n";
    return upperLinearGradient + gencCode + lowerLinearGradient;
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
    final String upperColor = "Color.fromRGBO(\n";
    final String lowerColor = ")\n";
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
    String width = "";
    if (!(fNode.getStrokes().isEmpty())) {
      width = "width:" + fNode.getStrokeWeight() + ",\n";
    }

    return upperBorder + width + bottomBorder;
  }
}
