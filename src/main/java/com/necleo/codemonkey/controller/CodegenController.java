package com.necleo.codemonkey.controller;

import com.necleo.codemonkey.consumer.request.FigmaNodeConsumerRequest;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.service.CodeGenService;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CodegenController {

  CodeGenService codeGenService;

  @PostMapping("/codegen")
  public Object postMethodName(@RequestBody FigmaNodeConsumerRequest entity) {
    Map<String, TagData> tagDataMap =
        entity.getTagData().stream()
            .collect(Collectors.toMap(TagData::getFigmaNodeId, tagData -> tagData));
    return codeGenService.gen(entity.getScreen(), tagDataMap);
  }
}
