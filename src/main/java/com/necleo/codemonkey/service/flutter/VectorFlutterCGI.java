package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.factory.FlutterFigmaNodeAbstractFactory;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.figma.FigmaVectorNode;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;

import java.util.Optional;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VectorFlutterCGI implements FlutterCGI {

  @Lazy
  FlutterFigmaNodeAbstractFactory flutterFigmaNodeFactory;
  @Override
  public Set<FigmaNodeMapper> getStrategy() {
    return Set.of(new FigmaNodeMapper(FigmaNodeTypes.VECTOR, null));
  }

  @Override
  public String generate(FigmaNode figmaNode, TagData tagData) {

    return generat(figmaNode, tagData);
  }

  private String generat(FigmaNode figmaNode, TagData tagData) {
    final String upperVector = "ClipPath(\n";
    final String bottomVector = ")\n";
    String genCode = "";
    genCode += "clipper: MyClipper(),\n";
    genCode += getChild(figmaNode,null);
    if (!(figmaNode instanceof FigmaVectorNode fNode)) {
      throw new IllegalArgumentException();
    }
    return genCode + "\n" + getClipperPath(fNode);
  }
  private String getChild(FigmaNode fNode, TagData tagData) {
    String genChild = "";
    FigmaNodeMapper figmaNodeMapper =
            new FigmaNodeMapper(fNode.getType(), null);
    Optional<FlutterCGI> flutterCGIOptional =
            flutterFigmaNodeFactory.getProcessor(figmaNodeMapper);
    genChild +=
            flutterCGIOptional
                    .map(flutterCGI -> flutterCGI.generate(fNode, null))
                    .orElse("");

    return "child:" + genChild + ",\n";
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
