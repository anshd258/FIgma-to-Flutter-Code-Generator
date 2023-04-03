package com.necleo.codemonkey.service.react;

import com.necleo.codemonkey.enums.FigmaNodeType;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.figma.FigmaEllipseNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EllipseReactCGI implements ReactCGI {
    @Override
    public String generate(FigmaNode figmaNode) {
        FigmaEllipseNode fNode = (FigmaEllipseNode) figmaNode;
        String genCode = "";

        genCode += "\n<div style={{ \n";
        genCode += getHeight(fNode);
        genCode += getWidth(fNode);
        genCode += getBoxDecoration(fNode);
        genCode += opacity(fNode);
        genCode += getHorizontalPosition(fNode);
        genCode += getVerticalPosition(fNode);
        genCode += getVisibility(fNode);

        genCode += " }}>";
        genCode += "</div>\n";
        System.out.println(genCode); // end indent

        return genCode;
    }

    @Override
    public FigmaNodeType getFigmaNodeType() {
        return FigmaNodeType.RECTANGLE;
    }

    public String getHeight(FigmaEllipseNode fNode) {
        if (fNode.getHeight() != 0) {
            return "height: '" + fNode.getHeight() + "px',\n";
        }
        return "height: '0px',\n";
    }

    public String getWidth(FigmaEllipseNode fNode) {
        if (fNode.getWidth() != 0) {
            return "width: '" + fNode.getWidth() + "px',\n";
        }
        return "width: '0px',\n";
    }

    public String getBoxDecoration(FigmaEllipseNode fNode) {
        final String upperBoxDecoration = "boxSizing: '";
        final String bottomBoxDecoration = ",\n";
        String genBoxDecoration;
        if (fNode.getBottomLeftRadius() != 0
                || fNode.getTopLeftRadius() != 0
                || fNode.getTopRightRadius() != 0
                || fNode.getBottomRightRadius() != 0) {
            genBoxDecoration = "border-box', \n border-radius: '";
            genBoxDecoration = genBoxDecoration + borderRadius(fNode) + "px',\n";
        } else genBoxDecoration = "unset',\n";
        if (fNode.getStrokeWeight() != 0) {
            genBoxDecoration += border(fNode);
        }
        return upperBoxDecoration + genBoxDecoration + bottomBoxDecoration;
    }

    public String borderRadius(FigmaEllipseNode fNode) {

        return Float.toString(fNode.getCornerRadius());
    }

    public String border(FigmaEllipseNode fNode) {
        final String fNodeColourR = (fNode.getFills().get(0).getColor().getR())+",";
        final String fNodeColourG = (fNode.getFills().get(0).getColor().getB())+",";
        final String fNodeColourB = String.valueOf(fNode.getFills().get(0).getColor().getB());

        final String upperBorder = "border: '";
        final String width = (fNode.getStrokeWeight()) + "px ";
        final String borderType = fNode.getStrokes().get(0).getType().toLowerCase() + " "; // this id must map the type of border ig
        final String colour = "rgb(" + fNodeColourR + fNodeColourG + fNodeColourB + ")";
        return upperBorder + width + borderType + colour ;
    }

    public String getHorizontalPosition(FigmaEllipseNode fNode) {
        return ("right: '" + (fNode.getX()) + "px',\n ");
    }

    public String getVerticalPosition(FigmaEllipseNode fNode) {
        return ("top: '" + (fNode.getY()) + "px',\n ");
    }

    public String getVisibility(FigmaEllipseNode fNode) {
        return ("visibility: " + fNode.isVisible() + ",\n");
    }

    public String opacity(FigmaEllipseNode fNode) {
        return ("opacity: '" + (fNode.getOpacity()) + "',\n ");
    }
}
