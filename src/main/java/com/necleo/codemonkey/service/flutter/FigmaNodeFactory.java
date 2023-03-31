package com.necleo.codemonkey.service.flutter;

import static com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes.RECTANGLE;

import com.necleo.codemonkey.lib.types.FigmaNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
