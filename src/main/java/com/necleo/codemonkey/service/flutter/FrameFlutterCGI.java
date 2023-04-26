package com.necleo.codemonkey.service.flutter;

import static com.necleo.codemonkey.lib.types.figma.properties.fills.enums.ScaleMode.FILL;

import com.necleo.codemonkey.factory.FlutterFigmaNodeAbstractFactory;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.figma.FigmaFrameNode;
import com.necleo.codemonkey.lib.types.figma.properties.fills.subtypes.FillsImage;
import com.necleo.codemonkey.lib.types.figma.properties.fills.subtypes.FillsSolid;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import java.util.Optional;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Slf4j


public class FrameFlutterCGI implements FlutterCGI {
  @Setter
          @Lazy@Autowired
  FlutterFigmaNodeAbstractFactory flutterFigmaNodeFactory;

  @Override
  public Set<FigmaNodeMapper> getStrategy() {
    return Set.of(new FigmaNodeMapper(FigmaNodeTypes.FRAME, null));
  }

  @Override
  public String generate(FigmaNode figmaNode, TagData tagData) {
    if (!(figmaNode instanceof FigmaFrameNode fNode)) {
      throw new IllegalArgumentException();
    }
    return generat(fNode, tagData);
  }

  private String generat(FigmaFrameNode figmaNode, TagData tagData) {
    String genCode = "";

    genCode += "\nContainer( \n";
    genCode += getHeight(figmaNode);
    genCode += getWidth(figmaNode);
    genCode += getPadding(figmaNode);
    //    if(!(figmaNode.getFills().equals(null))){
    //    genCode += getBoxDecoration(figmaNode);}


    genCode += "child:" + getchild(figmaNode, tagData) + ",\n";

    genCode += ")\n";
    System.out.println(genCode); // end indent

    return genCode;
  }

  private String getPadding(FigmaFrameNode figmaNode) {
    final String upperPadding = " padding: EdgeInsets.only(\n";
    final String lowerPadding = "),\n";
    String leftPaing = "left:" + figmaNode.getPaddingLeft() + ",";
    String rigt = "right:" + figmaNode.getPaddingRight() + ",";
    String top = "top:" + figmaNode.getPaddingTop() + ",";
    String bottom = "bottom:" + figmaNode.getPaddingBottom() + ",";
    return upperPadding + leftPaing + rigt + top + bottom + lowerPadding;
  }

  private String getchild(FigmaFrameNode figmaNode, TagData tagData) {
    String genCode = "";

    if (figmaNode.getLayoutMode().equals("NONE")) {
      final String upperStack = "Stack(\n";
      final String lowerStack = "),";
      genCode += "children:[\n";

      for (int i = (figmaNode.getChild().size()-1); i >= 0; i--) {
        String genChild = "";
        String gen = "";
        FigmaNodeMapper figmaNodeMapper =
            new FigmaNodeMapper(figmaNode.getChild().get(i).getType(), null);
        Optional<FlutterCGI> flutterCGIOptional =
            flutterFigmaNodeFactory.getProcessor(figmaNodeMapper);
        if (i == (figmaNode.getChild().size()-1)) {
          int finalI = i;
          genChild +=
              flutterCGIOptional
                  .map(flutterCGI -> flutterCGI.generate(figmaNode.getChild().get(finalI), null))
                  .orElse("");
        } else {
          int finalI = i;
          genChild +=
              flutterCGIOptional
                  .map(flutterCGI -> flutterCGI.generate(figmaNode.getChild().get(finalI), null))
                  .orElse("");
          gen = getPosition(genChild, figmaNode.getChild().get(finalI));
        }
        genCode += gen;
      }
      genCode += "],\n";

      return upperStack + genCode + lowerStack;

    } else if (figmaNode.getLayoutMode().equals("HORIZONTAL")) {
      final String upperRow = "Row(\n";
      final String lowerRow = "),\n";
      genCode += "children:[\n";

      for (int i = 0; i < figmaNode.getChild().size(); i++) {
        String genChild = "";
        String gen = "";
        FigmaNodeMapper figmaNodeMapper =
            new FigmaNodeMapper(figmaNode.getChild().get(i).getType(), null);
        Optional<FlutterCGI> flutterCGIOptional =
            flutterFigmaNodeFactory.getProcessor(figmaNodeMapper);
        if (i == (figmaNode.getChild().size()-1)) {
          int finalI = i;
          genChild +=
              flutterCGIOptional
                  .map(flutterCGI -> flutterCGI.generate(figmaNode.getChild().get(finalI), null))
                  .orElse("");
          genCode += genChild;
        } else {
          int finalI = i;
          genChild +=
              flutterCGIOptional
                  .map(flutterCGI -> flutterCGI.generate(figmaNode.getChild().get(finalI), null))
                  .orElse("");
          gen += getSpacing(figmaNode);
          genCode += genChild;
          genCode += gen;
        }
      }
      genCode += "],\n";
      return upperRow + genCode + lowerRow;
    } else if (figmaNode.getLayoutMode().name().equals("VERTICAL")) {
      final String upperColumn = "Column(\n";
      final String lowerColumn = "),\n";
      genCode += "children:[\n";

      for (int i = 0; i < figmaNode.getChild().size(); i++) {
        String genChild = "";
        String gen = "";
        FigmaNodeMapper figmaNodeMapper =
            new FigmaNodeMapper(figmaNode.getChild().get(i).getType(), null);
        Optional<FlutterCGI> flutterCGIOptional =
            flutterFigmaNodeFactory.getProcessor(figmaNodeMapper);
        if (i == (figmaNode.getChild().size()-1)) {
          int finalI = i;
          genChild +=
              flutterCGIOptional
                  .map(flutterCGI -> flutterCGI.generate(figmaNode.getChild().get(finalI), null))
                  .orElse("");
          genCode += genChild;
        } else {
          int finalI = i;
          genChild +=
              flutterCGIOptional
                  .map(flutterCGI -> flutterCGI.generate(figmaNode.getChild().get(finalI), null))
                  .orElse("");
          gen += getSpacing(figmaNode);
          genCode += genChild;
          genCode += gen;
        }
      }
      genCode += "],\n";
      return upperColumn + genCode + lowerColumn;
    }

    return "";
  }

  private String getSpacing(FigmaFrameNode figmaNode) {
    if (figmaNode.getLayoutMode().equals("HORIZONTAL")) {
      return "SizedBox(width:" + figmaNode.getItemSpacing() + ",),";
    } else {
      return "SizedBox(height:" + figmaNode.getItemSpacing() + ",),";
    }
  }

  private String getPosition(String genCode, FigmaNode figmaNode) {
    final String upperPosition = "  Positioned(\n";
    final String lowerPosition = "),\n";
    String top = "top:" + figmaNode.getY() + ",\n";
    String left = "left:" + figmaNode.getX() + ",\n";
    return upperPosition + genCode + lowerPosition;
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
