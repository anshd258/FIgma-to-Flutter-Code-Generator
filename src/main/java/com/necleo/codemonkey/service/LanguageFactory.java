package com.necleo.codemonkey.service;

import com.necleo.codemonkey.enums.Language;
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
public class LanguageFactory {

  final ApplicationContext applicationContext;

  Map<Language, CodeGen> codeGenMap;

  public CodeGen getCodeGenProcessor(Language type) {
    CodeGen processor = codeGenMap.get(type);
    if (processor == null) {
      throw new IllegalArgumentException(
          "No implementation found for PaymentProcessorType: " + type);
    }
    return processor;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void init() {
    codeGenMap = new HashMap<>();
    Map<String, CodeGen> beansOfType = applicationContext.getBeansOfType(CodeGen.class);

    beansOfType
        .values()
        .forEach(
            codeGen -> {
              Language type = codeGen.getLanguage();
              if (codeGenMap.containsKey(type)) {
                throw new IllegalStateException(
                    "Multiple implementations found for PaymentProcessorType: " + type);
              }
              codeGenMap.put(type, codeGen);
            });
  }
}
