package com.necleo.codemonkey.factory;

import com.necleo.codemonkey.factory.mapper.SetMapper;
import com.necleo.codemonkey.lib.types.enums.boilerplate.BoilerType;
import com.necleo.codemonkey.model.factory.BoilerNodeMapper;
import com.necleo.codemonkey.service.flutter.boilerplate.BoilerCGI;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FlutterBoilerTypeAbstractFactory extends AbstractFactory<BoilerNodeMapper, BoilerCGI> {
    protected FlutterBoilerTypeAbstractFactory(List<BoilerCGI> beans) {
        super(beans, new SetMapper<>());
    }
}
