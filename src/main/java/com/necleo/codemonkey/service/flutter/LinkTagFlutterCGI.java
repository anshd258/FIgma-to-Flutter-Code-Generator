package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.factory.FlutterFigmaNodeAbstractFactory;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.Tagdata.Props;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.TagDataType;
import com.necleo.codemonkey.lib.types.figma.FigmaTextNode;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LinkTagFlutterCGI implements FlutterCGI {

  @Lazy FlutterFigmaNodeAbstractFactory flutterFigmaNodeFactory;

  @Override
  public String generate(FigmaNode figmaNode, TagData tagData) {

    if (!(figmaNode instanceof FigmaTextNode fNode)) {
      throw new IllegalArgumentException();
    }
    return generat(fNode, tagData);
  }

  private String generat(FigmaTextNode fNode, TagData tagData) {
    final String upperButton = " InkWell(\n";
    final String lowerButton = "),\n";
    String genCode = "";
    genCode += getLink(tagData);
    genCode += getChild(fNode, tagData);
    return upperButton + genCode + lowerButton;
  }

  private String getChild(FigmaTextNode fNode, TagData tagData) {
    String genChild = "";
    FigmaNodeMapper figmaNodeMapper = new FigmaNodeMapper(fNode.getType(), null);
    Optional<FlutterCGI> flutterCGIOptional = flutterFigmaNodeFactory.getProcessor(figmaNodeMapper);
    genChild += flutterCGIOptional.map(flutterCGI -> flutterCGI.generate(fNode, null)).orElse("");

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
