package com.necleo.codemonkey.service.react;

import com.necleo.codemonkey.lib.types.FNode;
import com.necleo.codemonkey.lib.types.figma.FigmaRectangleNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class RectangleReactCGI implements ReactCGI <FigmaRectangleNode> {
    public String generate(FigmaRectangleNode fNode) {

        String genCode = "";

        genCode += "\n<div style=' \n";
        genCode += getHeight(fNode);
        genCode += getWidth(fNode);
        genCode += getBoxDecoration(fNode);
        genCode += opacity(fNode);
        genCode += getHorizontalPosition(fNode);
        genCode += getVerticalPosition(fNode);
        genCode += getVisibility(fNode);

        genCode += " '></div>,\n";
        System.out.println(genCode); // end indent

        return genCode;
    }

    public String getHeight(FigmaRectangleNode fNode) {
        if (fNode.getHeight() != 0) {
            return "height:" + Integer.toString(fNode.getHeight()) + ",\n";
        }
        return "height:0,\n";
    }

    public String getWidth(FigmaRectangleNode fNode) {
        if (fNode.getWidth() != 0) {
            return "width:" + Integer.toString(fNode.getWidth()) + ",\n";
        }
        return "width:0,\n";
    }

    public String getBoxDecoration(FigmaRectangleNode fNode) {
        final String upperBoxDecoration = "box-decoration:' ";
        final String bottomBoxDecoration = " ,\n";
        String genBoxDecoration = "";
        if (fNode.getBottomLeftRadius() != 0
                || fNode.getTopLeftRadius() != 0
                || fNode.getTopRightRadius() != 0
                || fNode.getBottomRightRadius() != 0) {
            genBoxDecoration = "border-box'; border-radius: '";
            genBoxDecoration = genBoxDecoration + borderRadius(fNode) + " ' ";
        }
        else
            genBoxDecoration = "none'";
        if (fNode.getStrokeWeight() != 0) {
            genBoxDecoration += border(fNode);
        }
        return upperBoxDecoration+ genBoxDecoration + bottomBoxDecoration;
    }

    public String borderRadius(FigmaRectangleNode fNode) {
//        final String upperBorderRadius = " borderRadius: BorderRadius.all(";
//        final String bottomBorderRadius = "),\n";
//        String topradiusL = " topLeft: Radius.circular(" + fNode.getTopLeftRadius() + "),\n";
//        String topradiusR =
//                " topRight: Radius.circular(" + Float.toString(fNode.getTopRightRadius()) + "),\n";
//        String bottomradiusL =
//                " bottomLeft:: Radius.circular(" + Float.toString(fNode.getBottomLeftRadius()) + "),\n";
//        String bottomradiusR =
//                " bottomRight: Radius.circular(" + Float.toString(fNode.getBottomRightRadius()) + "),\n";
//        return upperBorderRadius
//                + topradiusL
//                + topradiusR
//                + bottomradiusL
//                + bottomradiusR
//                + bottomBorderRadius;

        return Float.toString(fNode.getCornerRadius());
    }

    public String border(FigmaRectangleNode fNode) {
        final String upperBorder = " border: '";
        final String bottomBorder = "',\n";
        final String width =  Float.toString(fNode.getStrokeWeight());
        return upperBorder + width + bottomBorder;
    }

    public String getHorizontalPosition (FigmaRectangleNode fNode){
        return ("right: '" + Float.toString(fNode.x) + "' ");
    }

    public String getVerticalPosition (FigmaRectangleNode fNode){
        return  ("top: '" + Float.toString(fNode.y) + "' ");
    }

    public String getVisibility (FigmaRectangleNode fNode) {
        return ("visibility: '" + fNode.isVisible() + "' ");
    }

    public String opacity(FigmaRectangleNode fNode) {
        return ("visibility: '" + Float.toString(fNode.getOpacity()) + "' ");
    }


}
