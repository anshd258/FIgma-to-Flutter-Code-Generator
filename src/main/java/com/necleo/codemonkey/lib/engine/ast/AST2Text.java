package com.necleo.codemonkey.lib.engine.ast;

import com.necleo.codemonkey.lib.types.ASTNode;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AST2Text {

  public StringBuffer toText(ASTNode astNode) {
    StringBuffer stringBuffer =
        new StringBuffer(Optional.ofNullable(astNode.getValue()).orElse(""));
    for (ASTNode child : astNode.getChildren()) {
      stringBuffer.append(toText(child));
    }
    return stringBuffer;
  }
}
