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
public class InputReactTagCGI implements ReactCGI {

  RectangleReactCGI rectangleReactCGI;

  FrameReactCGI frameReactCGI;

  @Override
  public String generate(
          FigmaNode figmaNode, FigmaNode node, Map<String, TagData> tagDataMap, Set<String> importsFunctions) {

    return generat(figmaNode);
  }

  private String generat(FigmaNode fNode) {
    final String upperLink = "<label>" + getLabel(fNode) + "<input\n";
    final String lowerLink = "/></label>\n";
    // if input type = submit - return submitInput
    String submitInput =
        "<input type='submit' " + getStyles(fNode) + getSubmitValue(fNode) + "/>\n";
    String genCode = "";
    genCode += getInputName(fNode);
    genCode += getStyles(fNode);
    System.out.println(genCode); // end indent

    return upperLink + genCode + lowerLink;
  }

  private String getInputName(FigmaNode fNode) {
    return "name='test'\n";
  }

  private String getSubmitValue(FigmaNode fNode) {
    return "Submit";
  }

  private String getLabel(FigmaNode fNode) {
    return fNode.getName();
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

  public String getData(FigmaNode fNode) {
    return fNode.getName();
  }

  @Override
  public Set<FigmaNodeMapper> getStrategy() {
    return Set.of(
        new FigmaNodeMapper(FigmaNodeTypes.FRAME, TagDataType.INPUT),
        new FigmaNodeMapper(FigmaNodeTypes.RECTANGLE, TagDataType.INPUT));
  }
}
