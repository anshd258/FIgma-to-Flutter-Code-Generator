package com.necleo.codemonkey.factory;

import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.service.flutter.FlutterCGI;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FlutterFigmaNodeFactory extends Factory<FigmaNodeTypes, FlutterCGI> {

  @Autowired
  public FlutterFigmaNodeFactory(List<FlutterCGI> beans) {
    super(beans);
  }
}
