package com.necleo.codemonkey.service.react;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.figma.FigmaTextNode;

public class FrameReactCGI implements ReactCGI{
    @Override
    public FigmaNodeTypes getEnumMapping() {
        return FigmaNodeTypes.FRAME;
    }

    @Override
    public String generate(FigmaNode figmaNode) {
        FigmaTextNode fNode = (FigmaTextNode) figmaNode;
        String genCode = "";

        genCode += "\n<div className='container' style={{ \n";
        genCode += "width: full,\n";
        genCode += "height: 100%,\n";
        // genCode += generate(childrenNode);
        genCode += " }}>";
        genCode += "</div>\n";
        System.out.println(genCode); // end indent

        return genCode;
    }
}
