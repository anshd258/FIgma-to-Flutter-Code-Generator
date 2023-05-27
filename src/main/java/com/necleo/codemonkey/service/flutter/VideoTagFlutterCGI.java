package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.TagDataType;
import com.necleo.codemonkey.lib.types.figma.FigmaRectangleNode;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import com.necleo.codemonkey.model.factory.NecleoDataNode;
import java.util.Set;

import com.necleo.codemonkey.service.flutter.utils.SizeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VideoTagFlutterCGI implements FlutterCGI {
  SizeUtil sizeUtil = new SizeUtil();

  @Override
  public Set<FigmaNodeMapper> getStrategy() {
    return Set.of(
        new FigmaNodeMapper(FigmaNodeTypes.RECTANGLE, TagDataType.VIDEO),
        new FigmaNodeMapper(FigmaNodeTypes.RECTANGLE, TagDataType.YOUTUBE));
  }

  @Override
  public String generate(NecleoDataNode necleoDataNode) {
    if (!(necleoDataNode.fNode instanceof FigmaRectangleNode fNode)) {
      throw new IllegalArgumentException();
    }
    return generat(fNode, necleoDataNode.tagData, necleoDataNode);
  }

  private String generat(FigmaRectangleNode fNode, TagData tagData, NecleoDataNode necleoDataNode) {
    String initState = getInitState(tagData.getTagData().getProps().getUrl());
    String disposeState = getDispose();
    String genCode = getPlayer(fNode, necleoDataNode);
    return initState + disposeState + genCode;
  }

  private String getPlayer(FigmaRectangleNode fNode, NecleoDataNode necleoDataNode) {
    final String upperContainer = "Container(\n";
    final String lowerContainer = "),\n";
    String genCode = "";
    genCode += getSize(fNode, necleoDataNode);
    genCode += getChild();
    return upperContainer + genCode + lowerContainer;
  }

  private String getSize(FigmaRectangleNode fNode, NecleoDataNode necleoDataNode) {
    String width = sizeUtil.getWidth(fNode, necleoDataNode.mainScreen,necleoDataNode);
    String height = sizeUtil.getHeight(fNode, necleoDataNode.mainScreen,necleoDataNode);
    return width + height;
  }

  private String getChild() {
    final String upperFlickManager = "FlickVideoPlayer(flickManager: flickManager";
    final String lowerFlickManager = "),\n";
    String genProperty = "";
    return upperFlickManager + genProperty + lowerFlickManager;
  }

  private String getDispose() {
    String disposeState =
        " @override\n"
            + "  void dispose() {\n"
            + "    flickManager.dispose();\n"
            + "    super.dispose();\n"
            + "  }\n\n";
    return disposeState;
  }

  private String getInitState(String url) {
    final String upperInitState =
        " late FlickManager flickManager;\n"
            + "  @override\n"
            + "  void initState() {\n"
            + "    super.initState();\n"
            + "    flickManager = FlickManager(\n"
            + "      videoPlayerController: VideoPlayerController.network(";
    final String lowerInitState = "),\n" + "    );\n" + "  }\n\n";
    String uri = "";
    if (url != null) {
      uri += "'" + url + "'";
    }
    return upperInitState + uri + lowerInitState;
  }
}
