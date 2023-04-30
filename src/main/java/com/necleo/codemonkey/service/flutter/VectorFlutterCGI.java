package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.figma.FigmaVectorNode;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
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
  RectangleFlutterCGI rectangleFlutterCGI;

  @Override
  public Set<FigmaNodeMapper> getStrategy() {
    return Set.of(new FigmaNodeMapper(FigmaNodeTypes.VECTOR, null));
  }

  @Override
  public String generate(FigmaNode figmaNode, TagData tagData) {
    if (!(figmaNode instanceof FigmaVectorNode fNode)) {
      throw new IllegalArgumentException();
    }
    return generat((FigmaVectorNode) figmaNode, tagData);
  }

  private String generat(FigmaVectorNode figmaNode, TagData tagData) {
    final String upperVector = "ClipPath(\n";
    final String bottomVector = ")\n";
    String genCode = "";
    genCode += "clipper: MyClipper(),\n";
    genCode += "child:" + rectangleFlutterCGI.generate((FigmaNode) figmaNode, tagData) + ",\n";

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
