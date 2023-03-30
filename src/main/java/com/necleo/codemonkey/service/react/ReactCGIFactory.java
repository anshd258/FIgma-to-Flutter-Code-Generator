package com.necleo.codemonkey.service.react;

import com.necleo.codemonkey.enums.FigmaNodeType;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ReactCGIFactory {
  final ApplicationContext applicationContext;

  Map<FigmaNodeType, ReactCGI> codeGenMap;

  public ReactCGI getCodeGenProcessor(FigmaNodeType type) {

    ReactCGI processor = codeGenMap.get(type);
    if (processor == null) {
      throw new IllegalArgumentException(
          "No implementation found for PaymentProcessorType: " + type);
    }
    return processor;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void init() {
    codeGenMap = new HashMap<>();
    Map<String, ReactCGI> beansOfType = applicationContext.getBeansOfType(ReactCGI.class);
    beansOfType
        .values()
        .forEach(
            codeGen -> {
              FigmaNodeType type = codeGen.getFigmaNodeType();
              if (codeGenMap.containsKey(type)) {
                throw new IllegalStateException(
                    "Multiple implementations found for PaymentProcessorType: " + type);
              }
              codeGenMap.put(type, codeGen);
            });
  }
}
