package com.necleo.codemonkey.controller;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.service.CodeGenService;
import java.util.List;
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
  public Object postMethodName(@RequestBody List<FigmaNode> entity) {
    return codeGenService.gen(entity);
  }
}
