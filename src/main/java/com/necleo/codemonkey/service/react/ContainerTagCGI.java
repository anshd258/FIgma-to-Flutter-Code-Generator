package com.necleo.codemonkey.service.react;

import com.necleo.codemonkey.factory.ReactFigmaNodeAbstractFactory;
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
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ContainerTagCGI implements ReactCGI {

  @Lazy ReactFigmaNodeAbstractFactory figmaNodeFactory;

  @Override
  public String generate(
      FigmaNode figmaNode, Map<String, TagData> tagDataMap, Set<String> importsFunctions) {

    ReactCGI reactCGI =
        figmaNodeFactory.getProcessor(FigmaNodeMapper.of(figmaNode, tagDataMap)).orElseThrow();

    return reactCGI.generate(figmaNode, tagDataMap, importsFunctions);
  }

  @Override
  public Set<FigmaNodeMapper> getStrategy() {
    return Set.of(
        new FigmaNodeMapper(FigmaNodeTypes.FRAME, TagDataType.CONTAINER),
        new FigmaNodeMapper(FigmaNodeTypes.RECTANGLE, TagDataType.CONTAINER));
  }
}
