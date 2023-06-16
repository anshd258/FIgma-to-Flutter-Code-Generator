package com.necleo.codemonkey.service.flutter.componenthandler;

import com.necleo.codemonkey.flutter.index.FlutterGI;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.figma.FigmaComponentNode;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import com.necleo.codemonkey.model.factory.FlutterWI;
import com.necleo.codemonkey.service.flutter.FlutterCGI;
import java.util.Set;

public class ComponentFlutterCGI implements FlutterCGI {
  @Override
  public Set<FigmaNodeMapper> getStrategy() {
    return Set.of(new FigmaNodeMapper(FigmaNodeTypes.COMPONENT, null));
  }

  @Override
  public String generate(
      FigmaNode figmaNode, FigmaNode parentFigmaNode, FlutterGI flutterGI, FlutterWI flutterWI) {
    if (!(figmaNode instanceof FigmaComponentNode fNode)) {
      throw new IllegalArgumentException();
    }
    return generat(fNode, flutterWI.getTagData().get(figmaNode.getId()), flutterWI, flutterGI);
  }

  private String generat(
      FigmaComponentNode fNode, TagData tagData, FlutterWI flutterWI, FlutterGI flutterGI) {
    return "";
  }

  @Override
  public String generate(FlutterWI fultterNecleoDataNode, FigmaNode figmaNode) {
    return null;
  }
}
