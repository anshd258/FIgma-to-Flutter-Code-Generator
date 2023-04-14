package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.figma.FigmaVectorNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VectorFlutterCGI implements FlutterCGI {
  RectangleFlutterCGI rectangleFlutterCGI = new RectangleFlutterCGI();

  @Override
  public FigmaNodeTypes getEnumMapping() {
    return FigmaNodeTypes.VECTOR;
  }

  @Override
  public String generate(FigmaNode figmaNode) {
    if (!(figmaNode instanceof FigmaVectorNode fNode)) {
      throw new IllegalArgumentException();
    }
    return generat((FigmaVectorNode) figmaNode);
  }

  private String generat(FigmaVectorNode figmaNode) {
    final String upperVector = "ClipPath(\n";
    final String bottomVector = ")\n";
    String genCode = "";
    genCode += "clipper: MyClipper(),\n";
    genCode += "child:" + rectangleFlutterCGI.generate((FigmaNode) figmaNode) + ",\n";

    return genCode + "\n" + getClipperPath(figmaNode);
  }

  private String getClipperPath(FigmaVectorNode figmaNode) {
    final String upperClipper =
        "class MyClipper extends CustomClipper<Path> {\n"
            + "  final String pathData;\n"
            + "\n"
            + "  MyClipper(\n"
            + "      {this.pathData ='"
            + figmaNode.getFillGeometry().get(0).getData()
            + "'});\n";
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
