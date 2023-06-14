package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.factory.FlutterFigmaWidgetFactory;
import com.necleo.codemonkey.flutter.index.FlutterGI;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.Tagdata.Props;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.TagDataType;
import com.necleo.codemonkey.lib.types.figma.FigmaTextNode;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import com.necleo.codemonkey.model.factory.FlutterWI;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LinkTagFlutterCGI implements FlutterCGI {

  @Lazy
  FlutterFigmaWidgetFactory flutterFigmaNodeFactory;

  @Override
  public String generate(FigmaNode figmaNode, FigmaNode parentFigmaNode, FlutterGI flutterGI, FlutterWI flutterWI) {
    if (!(figmaNode instanceof FigmaTextNode fNode)) {
      throw new IllegalArgumentException();
    }
    return generat(fNode, flutterWI.getTagData().get(figmaNode.getId()), flutterWI,flutterGI);
  }

  @Override
  public String generate(FlutterWI fultterNecleoDataNode, FigmaNode figmaNode) {
    return null;
  }


  private String generat(
      FigmaTextNode fNode, TagData tagData, FlutterWI fultterNecleoDataNode,FlutterGI flutterGI) {
    final String upperButton = " InkWell(\n";
    final String lowerButton = "),\n";
    String genCode = "";
    genCode += getLink(tagData);
    genCode += getChild(fNode, tagData, fultterNecleoDataNode,flutterGI);
    return upperButton + genCode + lowerButton;
  }

  private String getChild(
      FigmaTextNode fNode, TagData tagData, FlutterWI fultterNecleoDataNode, FlutterGI flutterGI) {
    String genChild = "";
    FigmaNodeMapper figmaNodeMapper = new FigmaNodeMapper(fNode.getType(), null);
    Optional<FlutterCGI> flutterCGIOptional = flutterFigmaNodeFactory.getProcessor(figmaNodeMapper);
    genChild +=
        flutterCGIOptional.map(flutterCGI -> flutterCGI.generate(fNode,fNode,flutterGI,fultterNecleoDataNode)).orElse("");

    return "child:" + genChild + ",\n";
  }

  private String getLink(TagData tagData) {
    final String upperLink = " onTap: () async { \n";
    final String lowerLink = "},\n";
    String genLink = "";
    final Props props = tagData.getTagData().getProps();
    if (props.getLinkType().equals("url")) {
      genLink += "final Uri _url = Uri.parse('" + props.getPageUrl() + "');\n";
      genLink += getCheck("_url");
    }

    return upperLink + genLink + lowerLink;
  }

  private String getCheck(String url) {
    String genLink = "";
    String throwError = "}else{ throw 'Could not launch $url';";
    final String upperUrl = " if (await canLaunch(";
    final String midUrl = ")) {\n";
    final String lowerUrl = "}\n";
    genLink += getLauncher(url);

    return upperUrl + url + midUrl + genLink + throwError + lowerUrl;
  }

  private String getLauncher(String url) {
    final String upperLauncher = " await launch(";
    final String lowerLauncher = ");\n";
    return upperLauncher + url + lowerLauncher;
  }

  @Override
  public Set<FigmaNodeMapper> getStrategy() {
    return Set.of(new FigmaNodeMapper(FigmaNodeTypes.TEXT, TagDataType.LINK));
  }


}
