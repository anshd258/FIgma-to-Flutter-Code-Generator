package com.necleo.codemonkey.lib.engine.ast;

import com.necleo.codemonkey.lib.props.YamlProp;
import com.necleo.codemonkey.lib.props.html.MainHtmlYaml;
import com.necleo.codemonkey.lib.props.react.ComponentYaml;
import com.necleo.codemonkey.lib.props.react.H2Yaml;
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
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AstMaker {

  ComponentYaml componentYaml;

  H2Yaml h2Yaml;

  MainHtmlYaml mainHtmlYaml;

  public ASTNode ast(FNode fNode) {
    return null;
  }

  private ASTNode astFileNode(FNode fNode, YamlProp yamlProp, int level) {
    List<ASTNode> astChildren = new ArrayList<>();
    ASTType astType = ASTType.valueOf(yamlProp.getType().toUpperCase());
    pre(yamlProp, level, astChildren);
    ASTNode.ASTNodeBuilder astNodeBuilder =
        ASTNode.builder().type(astType).value(yamlProp.getValue());
    for (var child : yamlProp.getChildren()) {
      in(fNode, level, astChildren, child);
    }
    post(astType, yamlProp, level, astChildren);
    return astNodeBuilder.children(astChildren).build();
  }

  private void pre(YamlProp mainHtmlYaml, int level, List<ASTNode> astChildren) {
    if (level > 0) {
      astChildren.add(ASTNode.builder().type(ASTType.NODE).value(getIndent(level)).build());
    }
    //    if(level > 0 && mainHtmlYaml.getChildren().size() > 0) {
    //      astChildren.add(getNewLineNode());
    //    }
  }

  private void in(FNode fNode, int level, List<ASTNode> astChildren, YamlProp child) {
    if (child.getAction() == ASTAction.DOWNSTREAM) {
      for (var fchild : fNode.children()) {
        YamlProp yamlProp = null;
        //        if(fchild.getType().equals("h2")) {
        //            yamlProp = h2Yaml;
        //        }
        if (yamlProp == null) continue;
        astChildren.add(astFileNode(fchild, yamlProp, level + 1));
      }
      return;
    } else if (child.getAction() == ASTAction.RESOLVE) {
      return;
    }
    //    astChildren.add(ASTNode.builder().type(ASTType.NODE).value(getIndent(level)).build());
    //    ASTNode astChild = ASTNode.builder().type(ASTType.NODE).value(child.getValue()).build();
    astChildren.add(astFileNode(fNode, child, level));
  }

  private void post(ASTType astType, YamlProp mainHtmlYaml, int level, List<ASTNode> astChildren) {
    if (level > 0 && mainHtmlYaml.getChildren().size() > 0) {
      astChildren.remove(astChildren.size() - 1);
    }
    if (astType == ASTType.LINE) {
      astChildren.add(getNewLineNode());
    }
  }

  private ASTNode getNewLineNode() {
    return ASTNode.builder().type(ASTType.NODE).value("\n").build();
  }

  private String getIndent(int level) {
    StringBuilder space = new StringBuilder();
    while (level-- > 0) {
      space.append("\t");
    }
    return space.toString();
  }
}
