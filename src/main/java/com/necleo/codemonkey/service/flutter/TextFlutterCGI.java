package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.figma.FigmaRectangleNode;
import com.necleo.codemonkey.lib.types.figma.FigmaTextNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TextFlutterCGI implements FlutterCGI{




    @Override
    public String generate(FigmaNode figmaNode) {
        if (!(figmaNode instanceof FigmaTextNode fNode)) {
            throw new IllegalArgumentException();
        }
        return generat(fNode);
    }


    public String generat(FigmaTextNode fNode) {
        String genCode = "";
        final String upperText = "Text(\n";
        final String lowerText = "),\n";
        genCode += upperText;
        genCode += getText(fNode);
        genCode += getTextStyle(fNode);


        return genCode;
    }

    private String getTextStyle(FigmaTextNode fNode) {
        final String upperTextStyle = "style: const TextStyle(\n";
        final String lowerTextStyle = "),\n";
         String genTextStyle = "";
        if (fNode.getFills() != null){
            genTextStyle += getColor(fNode);
        }
        return upperTextStyle + genTextStyle + lowerTextStyle;
    }

    private String getColor(FigmaTextNode fNode) {

            final String upperColor = "color: Color.fromRGBO(\n";
            final String lowerColor = "),\n";
            final String red = Math.round(fNode.getFills().get(0).getColor().getR() * 255) + ",";
            final String green = Math.round(fNode.getFills().get(0).getColor().getG() * 255) + ",";
            final String blue = Math.round(fNode.getFills().get(0).getColor().getB() * 255) + ",";
            final String opacity = Float.toString(fNode.getFills().get(0).getOpacity());

            return upperColor + red + green + blue + opacity + lowerColor;
        }


    private String getText(FigmaTextNode fNode) {
        if (fNode.getName() != null){
            return fNode.getName() + "\n";
        }
       return "";
    }


    @Override
    public FigmaNodeTypes getEnumMapping() {
        return FigmaNodeTypes.TEXT;
    }
}
