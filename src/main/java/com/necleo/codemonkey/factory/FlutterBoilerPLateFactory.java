package com.necleo.codemonkey.factory;

import com.necleo.codemonkey.model.factory.BoilerNodeMapper;
import com.necleo.codemonkey.service.flutter.boilerplate.BoilerCGI;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FlutterBoilerPLateFactory extends AbstractFactory<BoilerNodeMapper, BoilerCGI> {
  protected FlutterBoilerPLateFactory(List<BoilerCGI> beans) {
    super(
        beans,
        ((BoilerCGI bean, Map<BoilerNodeMapper, BoilerCGI> map) -> {
          Set<BoilerNodeMapper> strategies = bean.getStrategy();
          strategies.forEach(
              strategy -> {
                if (map.containsKey(strategy)) {
                  throw new IllegalStateException(
                      "Multiple implementations found for Type: "
                          + strategy
                          + " and class: "
                          + bean.getClass().getName());
                }
                map.put(strategy, bean);
              });
        }));
  }
}
