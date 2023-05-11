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
public class TextAreaTagCGI implements ReactCGI {

  RectangleReactCGI rectangleReactCGI;
  FrameReactCGI frameReactCGI;

  @Override
  public String generate(
      FigmaNode figmaNode,
      FigmaNode node,
      Map<String, TagData> tagDataMap,
      Set<String> importsFunctions) {

    return generat(figmaNode);
  }

  private String generat(FigmaNode figmaNode) {
    String genCode = "";
    genCode +=
        "<textarea "
            + genStyles(figmaNode)
            + getRowsCols(figmaNode)
            + getName(figmaNode)
            + getPlaceholder(figmaNode)
            + " />";
    return genCode;
  }

  private String getPlaceholder(FigmaNode figmaNode) {
    String placeholder = "";
    placeholder += "placeholder='test textarea'";
    return placeholder;
  }

  private String getRowsCols(FigmaNode figmaNode) {
    if (figmaNode.getHeight() != 0 || figmaNode.getWidth() != 0) return "\n";
    return "\n";
  }

  private String genStyles(FigmaNode figmaNode) {
    String styles = "style={{";
    styles += "minHeight: " + figmaNode.getHeight() + ",\n";
    styles += "maxWidth: " + figmaNode.getWidth() + ",\n";
    styles += "resize: 'none',\n";
    if (figmaNode.getType().equals(FigmaNodeTypes.RECTANGLE)) {
      styles += rectangleReactCGI.getRectangleStyles(figmaNode);
    } else {
      styles += frameReactCGI.getStyles(figmaNode);
    }
    styles += "}}";
    return styles;
  }

  private String getName(FigmaNode figmaNode) {
    return "name='" + figmaNode.getName() + "'\n";
  }

  @Override
  public Set<FigmaNodeMapper> getStrategy() {
    return Set.of(
        new FigmaNodeMapper(FigmaNodeTypes.RECTANGLE, TagDataType.TEXTAREA),
        new FigmaNodeMapper(FigmaNodeTypes.FRAME, TagDataType.TEXTAREA));
  }
}
