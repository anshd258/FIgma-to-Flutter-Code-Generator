package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.factory.FlutterFigmaWidgetFactory;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.TagDataType;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import com.necleo.codemonkey.model.factory.FlutterWI;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Slf4j
// @RequiredArgsConstructor
// @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ButtonTagFlutterCGI implements FlutterCGI {
  @Lazy
  FlutterFigmaWidgetFactory flutterFigmaNodeFactory;

  //  FlutterFigmaWidgetFactory flutterFigmaNodeFactory;

  @Override
  public String generate(FlutterWI fultterNecleoDataNode) {

    return generat(
        fultterNecleoDataNode.figmaNode, fultterNecleoDataNode.tagData, fultterNecleoDataNode);
  }

  private String generat(
      FigmaNode fNode, TagData tagData, FlutterWI fultterNecleoDataNode) {
    final String upperButton = "GestureDetector(\n";

    final String lowerButton = "),\n";
    String genCode = "";
    genCode += getFunction();
    genCode += getChild(fNode, tagData, fultterNecleoDataNode);
    //    BoilerNodeMapper  boilerNodeMapper = new BoilerNodeMapper(BoilerType.STATELESS,null);
    //    Optional<BoilerCGI> flutterBoilerCGIOptional =
    // flutterBoilerTypeAbstractFactory.getProcessor(boilerNodeMapper);
    //    flutterBoilerCGIOptional.map(BoilerCGI::generate);

    return upperButton + genCode + lowerButton;
  }

  private String getChild(
      FigmaNode fNode, TagData tagData, FlutterWI fultterNecleoDataNode) {
    String genChild = "";
    FigmaNodeMapper figmaNodeMapper = new FigmaNodeMapper(fNode.getType(), null);
    Optional<FlutterCGI> flutterCGIOptional = flutterFigmaNodeFactory.getProcessor(figmaNodeMapper);
    genChild +=
        flutterCGIOptional.map(flutterCGI -> flutterCGI.generate(fultterNecleoDataNode)).orElse("");

    return "child:" + genChild + ",\n";
  }

  private String getFunction() {
    String genFunction = "";
    return " onTap: () {" + genFunction + "},\n";
  }

  @Override
  public Set<FigmaNodeMapper> getStrategy() {
    return Set.of(
        new FigmaNodeMapper(FigmaNodeTypes.FRAME, TagDataType.BUTTON),
        new FigmaNodeMapper(FigmaNodeTypes.RECTANGLE, TagDataType.BUTTON),
        new FigmaNodeMapper(FigmaNodeTypes.TEXT, TagDataType.BUTTON),
        new FigmaNodeMapper(FigmaNodeTypes.VECTOR, TagDataType.BUTTON),
        new FigmaNodeMapper(FigmaNodeTypes.LINE, TagDataType.BUTTON));
  }
}
