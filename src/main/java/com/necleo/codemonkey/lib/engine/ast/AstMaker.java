package com.necleo.codemonkey.lib.engine.ast;

import com.necleo.codemonkey.lib.props.html.MainHtmlYaml;
import com.necleo.codemonkey.lib.types.ASTNode;
import com.necleo.codemonkey.lib.types.FNode;
import com.necleo.codemonkey.lib.types.enums.ASTAction;
import com.necleo.codemonkey.lib.types.enums.ASTType;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AstMaker {

  final MainHtmlYaml mainHtmlYaml;

  public ASTNode ast(FNode fNode) {
    return astFileNode(fNode, ASTNode.builder().type(ASTType.ROOT).build(), mainHtmlYaml, 0);
  }

  private ASTNode astFileNode(FNode fNode, ASTNode astNode, MainHtmlYaml mainHtmlYaml, int level) {
    List<ASTNode> astChildren = new ArrayList<>();
    if(level > 0 && mainHtmlYaml.getChildren().size() > 0) {
      astChildren.add(ASTNode.builder().type(ASTType.NODE).value("\n").build());
    }
    for (var child : mainHtmlYaml.getChildren()) {
      if (child.getAction() == ASTAction.DOWNSTREAM) {
        continue;
      } else if (child.getAction() == ASTAction.RESOLVE) {
        continue;
      }
      astChildren.add(ASTNode.builder().type(ASTType.NODE).value(getIndent(level)).build());
      ASTNode astChild = ASTNode.builder().type(ASTType.NODE).value(child.getValue()).build();
      astChildren.add(astFileNode(fNode, astChild, child, level + 1));
      astChildren.add(ASTNode.builder().type(ASTType.NODE).value("\n").build());
    }
    if(level > 0 && mainHtmlYaml.getChildren().size() > 0) {
      astChildren.remove(astChildren.size() - 1);
    }
    astNode.setChildren(astChildren);
    return astNode;
  }

  private String getIndent(int level) {
    StringBuilder space = new StringBuilder();
    while(level-- > 0) {
      space.append("\t");
    }
    return space.toString();
  }
}
