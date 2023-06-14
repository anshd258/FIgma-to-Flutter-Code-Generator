package com.necleo.codemonkey.service.flutter;

import static com.necleo.codemonkey.lib.types.figma.properties.fills.enums.ScaleMode.FILL;

import com.necleo.codemonkey.flutter.index.FlutterGI;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.TagDataType;
import com.necleo.codemonkey.lib.types.figma.FigmaRectangleNode;
import com.necleo.codemonkey.lib.types.figma.properties.fills.subtypes.FillsImage;
import com.necleo.codemonkey.lib.types.figma.properties.fills.subtypes.FillsSolid;
import com.necleo.codemonkey.lib.types.figma.properties.strokes.Strokes;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import com.necleo.codemonkey.model.factory.FlutterWI;
import com.necleo.codemonkey.service.flutter.utils.SizeUtil;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SwitchTagFlutterCGI implements FlutterCGI {
  SizeUtil sizeUtil = new SizeUtil();

  @Override
  public Set<FigmaNodeMapper> getStrategy() {
    return Set.of(new FigmaNodeMapper(FigmaNodeTypes.RECTANGLE, TagDataType.SWITCH));
  }

  @Override
  public String generate(FigmaNode figmaNode, FigmaNode parentFigmaNode, FlutterGI flutterGI, FlutterWI flutterWI) {
    if (!(figmaNode instanceof FigmaRectangleNode fNode)) {
      throw new IllegalArgumentException();
    }
    return generat(fNode, flutterWI,flutterGI);
  }

  @Override
  public String generate(FlutterWI fultterNecleoDataNode, FigmaNode figmaNode) {
    return null;
  }


  private String generat(FigmaRectangleNode fNode, FlutterWI fultterNecleoDataNode, FlutterGI flutterGI) {
    final String upperStateFullWidget =
        "class CustomSwitch extends StatefulWidget {\n"
            + "  \n"
            + "\n"
            + "  @override\n"
            + "  _CustomSwitchState createState() => _CustomSwitchState();\n"
            + "}\n"
            + "\n"
            + "class _CustomSwitchState extends State<CustomSwitch>\n"
            + "    with SingleTickerProviderStateMixin {\n"
            + "  bool _isOn = false;\n"
            + "  late AnimationController _controller;\n"
            + "  late Animation<Offset> _offsetAnimation;\n"
            + "\n";
    final String lowerStateFullWidget = "}";
    final String initState = getInitState(fNode);

    final String dispose = getDispose();
    String genCode = "";
    genCode += getWidgetBuild(fNode, fultterNecleoDataNode, flutterGI);

    return upperStateFullWidget + initState + dispose + genCode + lowerStateFullWidget;
  }

  private String getWidgetBuild(
      FigmaRectangleNode fNode, FlutterWI fultterNecleoDataNode, FlutterGI flutterGI) {
    final String upperWidgetBuild =
        "@override\n"
            + "  Widget build(BuildContext context) {\n"
            + "    return GestureDetector(\n"
            + "      onTap: () {\n"
            + "        setState(() {\n"
            + "          _isOn = !_isOn;\n"
            + "          if (_isOn) {\n"
            + "            _controller.forward();\n"
            + "          } else {\n"
            + "            _controller.reverse();\n"
            + "          }\n"
            + "        });\n"
            + "      },\n"
            + "      child: AnimatedContainer(\n"
            + "        duration:";
    final String lowerWidgetBuild = " ),\n" + "    );\n" + "  }";
    String genCode = getDuration(300);
    genCode += sizeUtil.getHeight(fNode, fultterNecleoDataNode.getMainScreen(), flutterGI);
    genCode += sizeUtil.getWidth(fNode, fultterNecleoDataNode.getMainScreen(), flutterGI);
    genCode += getSwitchBoxDecoration(fNode);

    genCode += "child:" + getpadding(fNode);
    return upperWidgetBuild + genCode + lowerWidgetBuild;
  }

  private String getpadding(FigmaRectangleNode fNode) {
    final String upperPadding =
        "Padding(\n"
            + "          padding: EdgeInsets.symmetric(horizontal: 50 * 0.1),\n"
            + "          child: SlideTransition(\n"
            + "            position: _offsetAnimation,\n"
            + "            child: Row(\n"
            + "              mainAxisAlignment: MainAxisAlignment.start,\n"
            + "              children: [\n"
            + "                AnimatedContainer("
            + " duration:";
    final String lowerPadding =
        " ),\n" + "              ],\n" + "            ),\n" + "          ),\n" + "        ),";
    String genCode = "";
    genCode += getDuration(300);
    genCode += getHeightMultiFactor(fNode);
    genCode += getWidthMultiFactor(fNode);
    genCode += getBoxDecoration(fNode);
    return upperPadding + genCode + lowerPadding;
  }

  private String getWidthMultiFactor(FigmaRectangleNode fNode) {
    if (fNode.getWidth() != 0) {
      return "width:" + Integer.toString(fNode.getWidth()) + "* 0.2,\n";
    }
    return "width:0,\n";
  }

  private String getHeightMultiFactor(FigmaRectangleNode fNode) {
    if (fNode.getHeight() != 0) {
      return "height:" + Integer.toString(fNode.getHeight()) + "* 0.8 ,\n";
    }
    return "height:0,\n";
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

  private String getInitState(FigmaRectangleNode fNode) {
    final String upperInitState =
        "  @override\n"
            + "  void initState() {\n"
            + "    super.initState();\n"
            + "    _controller = AnimationController(\n"
            + "      vsync: this,\n"
            + "      duration: ";
    final String middleInitState =
        ");\n"
            + "    _offsetAnimation = Tween<Offset>(\n"
            + "      begin: Offset(0, 0),\n"
            + "      end:";
    final String endInitState =
        ").animate(CurvedAnimation(\n"
            + "      parent: _controller,\n"
            + "      curve: Curves.easeOut,\n"
            + "      reverseCurve: Curves.easeIn,\n"
            + "    ));\n"
            + "  }\n";
    String genCodeDuration = getDuration(300);
    String genCodeOffset = getOffset(fNode);
    return upperInitState + genCodeDuration + middleInitState + genCodeOffset + endInitState;
  }

  private String getDispose() {
    final String disposeMethod =
        "@override\n"
            + "  void dispose() {\n"
            + "    _controller.dispose();\n"
            + "    super.dispose();\n"
            + "  }";
    return disposeMethod;
  }

  private String getOffset(FigmaRectangleNode fNode) {
    final String upperOffSet = "Offset((";
    final String lowerOffset = ") / 210, 0),\n";
    return upperOffSet + fNode.getWidth() + lowerOffset;
  }

  private String getDuration(int i) {
    final String upperDuration = "Duration(milliseconds: ";
    final String lowerDuration = "),\n";
    return upperDuration + i + lowerDuration;
  }

  private String getSwitchBoxDecoration(FigmaRectangleNode fNode) {
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
    final String upperColor = "color: _isOn ? Color.fromRGBO(\n";
    final String lowerColor = ") : Colors.grey,\n";
    final String red = Math.round(fills.getColor().getR() * 255) + ",";
    final String green = Math.round(fills.getColor().getG() * 255) + ",";
    final String blue = Math.round(fills.getColor().getB() * 255) + ",";
    final String opacity = Double.toString(fills.getOpacity());

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
    final String upperColor = "color:  _isOn ? Color.fromRGBO(\n";
    final String lowerColor = ") :  Colors.grey,\n";
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

  private String getBoxDecoration(FigmaRectangleNode fNode) {
    final String upperBoxDecoration = "decoration: BoxDecoration(\n";
    final String bottomBoxDecoration = "),\n";
    String genBoxDecoration = "";

    if (fNode.getFills().get(0).getType().equals("SOLID")) {
      final FillsSolid fills = (FillsSolid) fNode.getFills().get(0);
      if (fills.getColor() != null && fNode.getFills().get(0).getOpacity() != 0) {
        genBoxDecoration += wGgetcolor(fills);
      }
    }
    System.out.println(fNode.getFills().get(0).getType());
    if (fNode.getFills().get(0).getType().equals("IMAGE")) {
      final FillsImage fills = (FillsImage) fNode.getFills().get(0);

      genBoxDecoration += Image(fills);
    }

    if (fNode.getBottomLeftRadius() != 0
        || fNode.getTopLeftRadius() != 0
        || fNode.getTopRightRadius() != 0
        || fNode.getBottomRightRadius() != 0) {
      genBoxDecoration += " shape: BoxShape.circle,";
    }
    if (fNode.getStrokeWeight() != 0) {
      genBoxDecoration += wGgetborder(fNode);
    }
    return upperBoxDecoration + genBoxDecoration + bottomBoxDecoration;
  }

  private String Image(FillsImage fills) {
    final String upperImage = " image: DecorationImage(\n";
    final String lowerImage = "),\n";
    String genImage = "";
    genImage += NetworkImage(fills);
    genImage += Fit(fills);
    return upperImage + genImage + lowerImage;
  }

  private String Fit(FillsImage fills) {
    String filltype = "";
    if (fills.getScaleMode().equals(FILL)) {
      filltype = "fill";
    }
    return "fit: BoxFit." + filltype + ",\n";
  }

  private String NetworkImage(FillsImage fills) {
    final String upperImage = " image: NetworkImage(\n";
    final String lowerImage = "),\n";
    final String imageUrl = "'" + fills.getImageHash() + "'";
    return upperImage + imageUrl + lowerImage;
  }

  private String wGgetcolor(FillsSolid fills) {
    final String upperColor = "color:  _isOn ? Colors.white\n";
    final String lowerColor = " : Colors.white,\n";

    return upperColor + lowerColor;
  }

  private String wGgetborder(FigmaRectangleNode fNode) {
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
