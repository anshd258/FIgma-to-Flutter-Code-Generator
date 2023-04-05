package com.necleo.codemonkey.service.react;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.figma.FigmaTextNode;

import java.util.Objects;

public class TextReactCGI implements ReactCGI {
    @Override
    public FigmaNodeTypes getEnumMapping() {
        return FigmaNodeTypes.RECTANGLE;
    }

    @Override
    public String generate(FigmaNode figmaNode) {
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

        style += "'alignItems: 'center', \n";

        return style;
    }

    public String getData(FigmaTextNode fNode) {
        if (!Objects.equals(fNode.getName(), ""))
            return " ";
        else
            return fNode.getName();
    }
}
