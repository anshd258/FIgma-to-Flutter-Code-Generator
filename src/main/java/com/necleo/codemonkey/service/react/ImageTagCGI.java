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
public class ImageTagCGI implements ReactCGI {
  RectangleReactCGI rectangleReactCGI;

  @Override
  public String generate(
      FigmaNode figmaNode,
      FigmaNode node,
      Map<String, TagData> tagDataMap,
      Set<String> importsFunctions) {

    return generat(figmaNode);
  }

  public String generat(FigmaNode fNode) {
    String genCode = "";
    if (fNode.getType().equals(FigmaNodeTypes.RECTANGLE)) {
      genCode += rectangleReactCGI.generat(fNode);
    }
    return genCode;
  }

  @Override
  public Set<FigmaNodeMapper> getStrategy() {
    return Set.of(new FigmaNodeMapper(FigmaNodeTypes.RECTANGLE, TagDataType.IMAGE));
  }
}
