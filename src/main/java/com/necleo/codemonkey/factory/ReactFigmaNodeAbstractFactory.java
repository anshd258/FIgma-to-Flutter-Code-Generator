package com.necleo.codemonkey.factory;

import com.necleo.codemonkey.factory.mapper.SetMapper;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import com.necleo.codemonkey.service.react.ReactCGI;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReactFigmaNodeAbstractFactory extends AbstractFactory<FigmaNodeMapper, ReactCGI> {

  @Autowired
  public ReactFigmaNodeAbstractFactory(List<ReactCGI> beans) {
    super(beans, new SetMapper<>());
  }
}
