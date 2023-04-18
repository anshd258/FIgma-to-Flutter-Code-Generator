package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.figma.FigmaRectangleNode;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import com.necleo.codemonkey.lib.types.FigmaNode;

import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.TagDataType;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class ButtonTagCGI implements FlutterCGI {


RectangleFlutterCGI rectangleFlutterCGI = new RectangleFlutterCGI();
TextFlutterCGI textFlutterCGI = new TextFlutterCGI();
FrameFlutterCGI frameFlutterCGI = new FrameFlutterCGI();


    @Override
    public String generate(FigmaNode figmaNode, TagData tagData) {

        return generat(figmaNode,tagData);
    }

    private String generat(FigmaNode fNode, TagData tagData) {
        final String upperButton = "GestureDetector(\n";

        final String lowerButton = "),\n";
        String genCode = "";
        genCode += getFunction();
        genCode += getChild(fNode,tagData);


        return upperButton + genCode + lowerButton;
    }

    private String getChild(FigmaNode fNode, TagData tagData) {
        String genChild = "";
        if (fNode.getType().equals(FigmaNodeTypes.RECTANGLE)){
            genChild += rectangleFlutterCGI.generat((FigmaRectangleNode) fNode);
        } else if (fNode.getType().equals(FigmaNodeTypes.TEXT)) {
            genChild += textFlutterCGI.generate(fNode,tagData);

        } else if (fNode.getType().equals(FigmaNodeTypes.FRAME)) {
            genChild += frameFlutterCGI.generate(fNode,tagData);
        }
        return "child:" + genChild + ",\n";
    }

    private String getFunction() {
        String genFunction = "";
        return  " onTap: () {" + genFunction + "},\n";
    }



  @Override
  public Set<FigmaNodeMapper> getStrategy() {
    return Set.of(new FigmaNodeMapper(FigmaNodeTypes.FRAME, TagDataType.BUTTON), new FigmaNodeMapper(FigmaNodeTypes.RECTANGLE, TagDataType.BUTTON));
  }

}
