package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.configuration.S3FileLoader;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.figma.FigmaVectorNode;
import com.necleo.codemonkey.lib.types.figma.properties.fills.subtypes.FillsSolid;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import com.necleo.codemonkey.model.factory.NecleoDataNode;
import java.util.Set;

import com.necleo.codemonkey.service.flutter.utils.ClipperUtil;
import com.necleo.codemonkey.service.flutter.utils.SizeUtil;
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
  ClipperUtil clipperUtil = new ClipperUtil();
  S3FileLoader s3FileLoader;
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
    if(!necleoDataNode.imports.contains("MYCLIPPER")){
      necleoDataNode.imports.add("MYCLIPPER");
      necleoDataNode.packages.add("SVG_PATH_PARSER");
      clipperUtil.getClipperPath();
    }

    genCode += "clipper: MyClipper(\"" + ((FigmaVectorNode) figmaNode).getFillGeometry().get(0).data + " \"),\n";
    genCode += getChild((FigmaVectorNode) figmaNode, null , necleoDataNode);

    return upperVector + genCode + bottomVector + "\n" ;
  }

  private String getChild(FigmaVectorNode fNode, TagData tagData, NecleoDataNode necleoDataNode) {
    String genChild = "";
    genChild += "\nContainer( \n";
    genChild += sizeUtil.getHeight(fNode, necleoDataNode.mainScreen,necleoDataNode);
    genChild += sizeUtil.getWidth(fNode, necleoDataNode.mainScreen,necleoDataNode);
    if(!(fNode.getFills().isEmpty())){
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


}
