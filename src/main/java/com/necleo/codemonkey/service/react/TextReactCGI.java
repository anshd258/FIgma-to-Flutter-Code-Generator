package com.necleo.codemonkey.service.react;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.figma.FigmaTextNode;
import java.util.Objects;

public class TextReactCGI implements ReactCGI {
    @Override
    public FigmaNodeTypes getEnumMapping() {
        return FigmaNodeTypes.TEXT;
    }

    @Override
    public String generate(FigmaNode figmaNode) {
        FigmaTextNode fNode = (FigmaTextNode) figmaNode;
        String genCode = "";

        genCode += "\n<div style={{ \n";
        genCode += getStyle(fNode);
        genCode += " }}>";
        genCode += getData(fNode);
        genCode += "</div>\n";
        System.out.println(genCode); // end indent

        return genCode;
    }


    public String getStyle(FigmaTextNode fNode){
        String style = "";
        // visible
        if (fNode.isVisible())
            style += "visibility: true, \n";
        else
            style += "visibility: false, \n";
        // color
        style += "color: 'rgb(" +
                fNode.getFills().get(0).getColor().getR() + ", " +
                fNode.getFills().get(0).getColor().getG() + ", " +
                fNode.getFills().get(0).getColor().getB()
                + ")'\n";
        // align
        style += "left: '" + fNode.getX() + "',\n";
        style += "top: '" + fNode.getY() + "',\n";
        style += "opacity: '" + fNode.getOpacity() + "',\n";
        style += "border: '" + fNode.getStrokes().get(0).getType().toLowerCase() + ", " + fNode.getStrokeWeight() + "px, rgb(" + fNode.getStrokes().get(0).getColor().getR() + ", " + fNode.getStrokes().get(0).getColor().getG() + ", " + fNode.getStrokes().get(0).getColor().getB() + ")',\n";
        style += getAlignment(fNode);
        style += getFont(fNode);

        return style;
    }

    public String getData(FigmaTextNode fNode) {
        if (!Objects.equals(fNode.getName(), ""))
            return " ";
        else
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

        alignment += "justifyContent: '"+ fNode.getPrimaryAxisAlignitems().toString().toLowerCase() +"',\n";
        alignment += "lineHeight: '"+ fNode.getLineHeight() +"'\n";
        return alignment;
    }
}
