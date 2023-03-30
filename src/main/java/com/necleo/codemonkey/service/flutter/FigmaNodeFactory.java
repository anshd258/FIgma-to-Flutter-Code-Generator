package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.lib.types.FigmaNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes.RECTANGLE;

@Service
@Slf4j
public class FigmaNodeFactory {

  public FlutterCGI getNode(FigmaNode fNode) {
    if (fNode.getType() == RECTANGLE) {

      return new RectangleFlutterCGI();
    }
    return null;
  }
}
