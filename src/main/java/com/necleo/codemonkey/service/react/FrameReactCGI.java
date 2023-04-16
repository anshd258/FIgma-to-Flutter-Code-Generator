package com.necleo.codemonkey.service.react;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.figma.FigmaFrameNode;
import com.necleo.codemonkey.lib.types.figma.FigmaRectangleNode;
import com.necleo.codemonkey.lib.types.figma.FigmaTextNode;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@Slf4j
public class FrameReactCGI implements ReactCGI{

    RectangleReactCGI rectangleReactCGI = new RectangleReactCGI();
    TextReactCGI textReactCGI = new TextReactCGI();


    @Override
    public String generate(FigmaNode figmaNode) {
        FigmaFrameNode fNode = (FigmaFrameNode) figmaNode;
        String genCode = "";

        genCode += "\n<div className='container' style={{ \n";
        genCode += getStyles(figmaNode);
        genCode += " }}>";
        while (!fNode.getChild().get(0).getName().equals("")) {
            genCode += getChild(fNode);
        }
        genCode += "</div>\n";
        System.out.println(genCode); // end indent

        return genCode;

    }

    public String getStyles(FigmaNode figmaNode){
        FigmaFrameNode fNode = (FigmaFrameNode) figmaNode;
        String styles = "";
        styles += "width: full,\n";
        styles += "height: 100%,\n";
        // genCode += generate(childrenNode);
        if (fNode.getLayoutMode().equals("AUTO")) {
            styles += getAutoLayout(fNode);
        }
        styles += "gap: '" + fNode.getItemSpacing()+ "px',\n";
        styles += getPadding(fNode);
        return styles;
    }

    public String getAutoLayout(FigmaFrameNode fNode){
        String autoLS = "";
        autoLS += "display: flex";

        return autoLS;
    }


    public String getPadding(FigmaFrameNode fNode){
        String padding = "";
        padding += "paddingTop: '" + fNode.getPaddingTop() + "px',\n";
        padding += "paddingBottom: '" + fNode.getPaddingBottom() + "px',\n";
        padding += "paddingLeft: '" + fNode.getPaddingLeft() + "px',\n";
        padding += "paddingRight: '" + fNode.getPaddingRight() + "px',\n";

        return padding;
    }

    public String getChild(FigmaFrameNode fNode){
        String childData = "";
        String childType = fNode.getChild().get(0).getName();
        if (childType.equals("TEXT"))
            childData += textReactCGI.generate((FigmaNode) fNode);

        else if (childType.equals("RECTANGLE"))
            childData += rectangleReactCGI.generate((FigmaNode) fNode);

        return childData;
    }

    @Override
    public Set<FigmaNodeMapper> getStrategy() {
        return Set.of(new FigmaNodeMapper(FigmaNodeTypes.FRAME, null));
    }
}
