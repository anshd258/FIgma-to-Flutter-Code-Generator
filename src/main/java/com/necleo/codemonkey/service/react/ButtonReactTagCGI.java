package com.necleo.codemonkey.service.react;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.TagDataType;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import java.util.Map;
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
public class ButtonReactTagCGI implements ReactCGI {

  RectangleReactCGI rectangleReactCGI;
  TextReactCGI textReactCGI;
  FrameReactCGI frameReactCGI;
  //    FigmaNodeMapper figmaNodeMapper = new FigmaNodeMapper();

  @Override
  public String generate(
          FigmaNode figmaNode, FigmaNode node, Map<String, TagData> tagDataMap, Set<String> importsFunctions) {

    return generat(figmaNode, importsFunctions, tagDataMap);
  }

  private String generat(FigmaNode fNode, Set<String> importsFunctions, Map<String, TagData> tagDataMap) {
    final String upperButton = "<button\n";

    final String lowerButton = "> " + getData(fNode, importsFunctions, tagDataMap) + " </button>\n";
    String genCode = "";
    genCode += getClickFunction(fNode);
    //        genCode += getChild(fNode);
    genCode += getStyles(fNode);
    System.out.println(genCode); // end indent

    return upperButton + genCode + lowerButton;
  }

  public String getData(FigmaNode fNode, Set<String> importsFunctions, Map<String, TagData> tagDataMap) {
    String child = "";
    if (fNode.getChild().get(0).getType().equals(FigmaNodeTypes.TEXT)) {
      //            FigmaTextNode figmaTextNode = (FigmaTextNode) fNode.getChild().get(0);
      //            child += figmaTextNode.getCharacters();
      child += textReactCGI.generate(fNode.getChild().get(0), fNode, tagDataMap, importsFunctions);
    }
    return child;
  }

  private String getStyles(FigmaNode fNode) {
    String styles = "style={{";
    if (fNode.getType().equals(FigmaNodeTypes.RECTANGLE)) {
      styles += rectangleReactCGI.getRectangleStyles(fNode);
    } else if (fNode.getType().equals(FigmaNodeTypes.FRAME)) {
      styles += frameReactCGI.getStyles(fNode);
    }
    return styles + "}}";
  }

  private String getClickFunction(FigmaNode figmaNode) {
    String onClkFunc = "";
    String ClkAction = "      window.location.href='";
    onClkFunc +=
        """
                onClick={(e) => {
                      e.preventDefault();
                """;
    String endFunc = """
              ';

              }}
                """;
    //        if ( figmaNode.getId())
    //        ClkAction += "http://google.com";
    ClkAction += "";
    return onClkFunc + ClkAction + endFunc;
  }

  @Override
  public Set<FigmaNodeMapper> getStrategy() {
    return Set.of(
        new FigmaNodeMapper(FigmaNodeTypes.FRAME, TagDataType.BUTTON),
        new FigmaNodeMapper(FigmaNodeTypes.RECTANGLE, TagDataType.BUTTON));
  }
}
