package com.necleo.codemonkey.service.react;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.figma.FigmaTextNode;
import com.necleo.codemonkey.lib.types.figma.properties.fills.subtypes.FillsSolid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class TextReactCGI implements ReactCGI {
    @Override
    public FigmaNodeTypes getEnumMapping() {
        return FigmaNodeTypes.TEXT;
    }


    @Override
    public String generate(FigmaNode figmaNode) {
        if (!(figmaNode instanceof FigmaTextNode fNode)) {
            throw new IllegalArgumentException();
        }
        return generat(fNode);
    }

    public String generat(FigmaNode figmaNode) {
        FigmaTextNode fNode = (FigmaTextNode) figmaNode;
        String genCode = "";

        genCode += "\n<p style={{ \n";
        genCode += getStyle(fNode);
        genCode += " }}>";
        genCode += getData(fNode);
        genCode += "</p>\n";
        System.out.println(genCode); // end indent

        return genCode;
    }


    public String getStyle(FigmaTextNode fNode){
        String style = "";
        final FillsSolid fills = (FillsSolid) fNode.getFills().get(0);
        // visible
        if (fNode.isVisible())
            style += "visibility: true, \n";
        else
            style += "visibility: false, \n";
        // color
        style += "color: 'rgb(" +
                (fills.getColor().getR() * 255) + ", " +
                (fills.getColor().getG() *255) + ", " +
                (fills.getColor().getB() * 255)
                + ")'\n";
        // align
        style += "left: '" + fNode.getX() + "',\n";
        style += "top: '" + fNode.getY() + "',\n";
        style += "opacity: '" + fNode.getOpacity() + "',\n";
        style += "border: '"
                + fNode.getStrokes().get(0).getType().toLowerCase()
                + ", "
                + fNode.getStrokeWeight()
                + "px, rgb(" + fNode.getStrokes().get(0).getColor().getR() * 255
                + ", " + fNode.getStrokes().get(0).getColor().getG() * 255
                + ", " + fNode.getStrokes().get(0).getColor().getB() * 255
                + ")',\n";
        style += getAlignment(fNode);
        style += getFont(fNode);

        return style;
    }

    public String getData(FigmaTextNode fNode) {
//        if (!Objects.equals(fNode.getName(), ""))
//            return " ";
//        else
            return fNode.getName();
    }

    public String getFont(FigmaTextNode fNode) {
        String font = "";
        font += "fontWeight: '" + fNode.getFontWeight() + "',\n ";
        font += "fontFace: '" + fNode.getFontFamily() + "',\n";
        font += "fontSize: '" + fNode.getFontSize() + "',\n";

        return  font;
    }

    public String getAlignment(FigmaTextNode fNode) {
        String alignment = "";

//       alignment += "justifyContent: '"+ (fNode.getPrimaryAxisAlignitems() ? fNode.getPrimaryAxisAlignitems().toString().toLowerCase()  :'' )  +"',\n";
        alignment += "lineHeight: '"+ fNode.getLineHeight() +"'\n";
        return alignment;
    }
}
