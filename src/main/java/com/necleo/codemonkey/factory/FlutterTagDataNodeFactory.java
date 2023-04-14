package com.necleo.codemonkey.factory;

import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.TadDataType;
import com.necleo.codemonkey.service.flutter.TagFlutterCGI;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FlutterTagDataNodeFactory extends Factory<TadDataType, TagFlutterCGI> {

  @Autowired
  public FlutterTagDataNodeFactory(List<TagFlutterCGI> beans) {
    super(beans);
  }
}
