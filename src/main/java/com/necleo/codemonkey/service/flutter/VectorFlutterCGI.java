package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.figma.FigmaVectorNode;
import com.necleo.codemonkey.lib.types.figma.properties.fills.subtypes.FillsSolid;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import com.necleo.codemonkey.model.factory.NecleoDataNode;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VectorFlutterCGI implements FlutterCGI {
  SizeUtil sizeUtil = new SizeUtil();

  @Override
  public Set<FigmaNodeMapper> getStrategy() {
    return Set.of(new FigmaNodeMapper(FigmaNodeTypes.VECTOR, null));
  }

  @Override
  public String generate(NecleoDataNode necleoDataNode) {

    return generat(necleoDataNode.fNode, necleoDataNode);
  }

  private String generat(FigmaNode figmaNode, NecleoDataNode necleoDataNode) {
    final String upperVector = "ClipPath(\n";
    final String bottomVector = ")\n";
    String genCode = "";
    necleoDataNode.imports.add("MYCLIPPER");
    genCode += "clipper: MyClipper(" + ((FigmaVectorNode) figmaNode).getFillGeometry().get(0).data + " \"),\n";
    genCode += getChild((FigmaVectorNode) figmaNode, null);
    FigmaVectorNode fNode = (FigmaVectorNode) figmaNode;
    return genCode + "\n" ;
  }

  private String getChild(FigmaVectorNode fNode, TagData tagData) {
    String genChild = "";
    genChild += "\nContainer( \n";
    genChild += sizeUtil.getHeight(fNode);
    genChild += sizeUtil.getWidth(fNode);
    if(fNode.getFills() != null){
      if (fNode.getFills().get(0).getType().equals("SOLID")) {
        final FillsSolid fills = (FillsSolid) fNode.getFills().get(0);
        if (fills.getColor() != null && fNode.getFills().get(0).getOpacity() != 0) {
          genChild += "color:" + color(fills) + ",\n";
        }
    }

    }

    return "child:" + genChild + ",\n";
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

  private String getClipperPath(FigmaVectorNode figmaNode) {
    final String upperClipper =
        "class MyClipper extends CustomClipper<Path> {\n"
            + "  final String pathData;\n"
            + "\n"
            + "  MyClipper(\n"
            + "      {required this.pathData});\n";
    final String lowerClipper =
        "  @override\n"
            + "  Path getClip(Size size) {\n"
            + "    Path path = parseSvgPath(pathData);\n"
            + "   \n"
            + "    return path;\n"
            + "  }\n"
            + "\n"
            + "  @override\n"
            + "  bool shouldReclip(CustomClipper<Path> oldClipper) => false;\n"
            + "}";

    return upperClipper + lowerClipper;
  }
}
