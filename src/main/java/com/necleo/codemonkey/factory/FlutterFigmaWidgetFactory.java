package com.necleo.codemonkey.factory;

import com.necleo.codemonkey.factory.mapper.SetMapper;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import com.necleo.codemonkey.service.flutter.FlutterCGI;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FlutterFigmaWidgetFactory extends AbstractFactory<FigmaNodeMapper, FlutterCGI> {

  public FlutterFigmaWidgetFactory(List<FlutterCGI> beans) {
    super(beans, new SetMapper<>());
  }
}
