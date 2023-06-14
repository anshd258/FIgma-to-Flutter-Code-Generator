package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.factory.FlutterFigmaWidgetFactory;
import com.necleo.codemonkey.flutter.index.FlutterGI;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.TagDataType;
import com.necleo.codemonkey.lib.types.figma.FigmaRectangleNode;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import com.necleo.codemonkey.model.factory.FlutterWI;
import java.util.Optional;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileTagFlutterCGI implements FlutterCGI {

  @Lazy
  FlutterFigmaWidgetFactory flutterFigmaNodeFactory;

  @Override
  public Set<FigmaNodeMapper> getStrategy() {
    return Set.of(
        new FigmaNodeMapper(FigmaNodeTypes.FRAME, TagDataType.FILE),
        new FigmaNodeMapper(FigmaNodeTypes.RECTANGLE, TagDataType.FILE),
        new FigmaNodeMapper(FigmaNodeTypes.TEXT, TagDataType.FILE),
        new FigmaNodeMapper(FigmaNodeTypes.VECTOR, TagDataType.FILE),
        new FigmaNodeMapper(FigmaNodeTypes.LINE, TagDataType.FILE));
  }

  @Override
  public String generate(FigmaNode figmaNode, FigmaNode parentFigmaNode, FlutterGI flutterGI, FlutterWI flutterWI) {
    if (!(figmaNode instanceof FigmaRectangleNode fNode)) {
      throw new IllegalArgumentException();
    }
    return generat(fNode, flutterWI,flutterGI);
  }

  @Override
  public String generate(FlutterWI fultterNecleoDataNode, FigmaNode figmaNode) {
    return null;
  }


  private String generat(FigmaRectangleNode fNode, FlutterWI fultterNecleoDataNode,FlutterGI flutterGI) {
    String widget = getWidget(fNode, fultterNecleoDataNode,flutterGI);
    String function = getFunction();

    return widget + function;
  }

  private String getFunction() {
    final String upperFunction =
        "Future<void> _openFileExplorer() async {\n"
            + "    try {\n"
            + "      FilePickerResult? result = await FilePicker.platform.pickFiles();\n"
            + "      if (result != null) {\n"
            + " File file = File(result.files.single.path!);\n";
    final String lowerFunction =
        " }\n" + "    } catch (e) {\n" + "      print(e);\n" + "    }\n" + "  }\n\n";
    String genFunction = "";
    genFunction += getFileName();
    return upperFunction + genFunction + lowerFunction;
  }

  private String getFileName() {
    String fileNameAndPath =
        "_fileName = p.basename(file.path);\n" + "          _filePath = file.path;\n";
    return fileNameAndPath;
  }

  private String getWidget(FigmaRectangleNode fNode, FlutterWI fultterNecleoDataNode,FlutterGI flutterGI) {
    final String upperButton = "GestureDetector(\n";

    final String lowerButton = "),\n\n";
    String genCode = "";
    genCode += getOnclick();
    genCode += getChild(fNode, fultterNecleoDataNode,flutterGI);

    return upperButton + genCode + lowerButton;
  }

  private String getChild(FigmaRectangleNode fNode, FlutterWI fultterNecleoDataNode,FlutterGI flutterGI) {
    String genChild = "";
    FigmaNodeMapper figmaNodeMapper = new FigmaNodeMapper(fNode.getType(), null);
    Optional<FlutterCGI> flutterCGIOptional = flutterFigmaNodeFactory.getProcessor(figmaNodeMapper);
    genChild +=
        flutterCGIOptional.map(flutterCGI -> flutterCGI.generate(fNode,fNode,flutterGI,fultterNecleoDataNode)).orElse("");
    return "child:" + genChild + ",\n";
  }

  private String getOnclick() {
    String genFunction = "await _openFileExplorer();";
    return " onTap: () async{" + genFunction + "},\n";
  }


}
