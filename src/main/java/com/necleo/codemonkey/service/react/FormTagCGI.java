package com.necleo.codemonkey.service.react;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.TagDataType;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FormTagCGI implements ReactCGI {

  RectangleReactCGI rectangleReactCGI;

  FrameReactCGI frameReactCGI;

  @Override
  public Set<FigmaNodeMapper> getStrategy() {
    return Set.of(
        new FigmaNodeMapper(FigmaNodeTypes.FRAME, TagDataType.FORM),
        new FigmaNodeMapper(FigmaNodeTypes.RECTANGLE, TagDataType.FORM));
  }

  @Override
  public String generate(
      FigmaNode figmaNode, Map<String, TagData> tagDataMap, Set<String> importsFunctions) {

    return generat(figmaNode);
  }

  private String generat(FigmaNode fNode) {
    String genCode = "";
    genCode += "<form>";
    genCode += getStyles(fNode) + "onSubmit=" + submitHandler(fNode) + ">";
    //        genCode += "child";
    genCode += "</form>\n";
    System.out.println(genCode); // end indent

    return genCode;
  }

  private String submitHandler(FigmaNode fNode) {
    String func = "() => {";
    func += " ";
    func += "}";
    return func;
  }

  private String getSubmitValue(FigmaNode fNode) {
    return "Submit";
  }

  private String getLabel(FigmaNode fNode) {
    return fNode.getName();
  }

  private String getStyles(FigmaNode fNode) {
    String styles = "styles={{";

    if (fNode.getType().equals(FigmaNodeTypes.RECTANGLE)) {
      styles += rectangleReactCGI.getRectangleStyles(fNode);
    } else if (fNode.getType().equals(FigmaNodeTypes.FRAME)) {
      styles += frameReactCGI.getStyles(fNode);
    }
    return styles + "}}";
  }
}
