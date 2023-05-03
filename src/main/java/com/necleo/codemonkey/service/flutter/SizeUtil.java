package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.lib.types.FigmaNode;

public class SizeUtil {
    public String getHeight(FigmaNode fNode) {
        if (fNode.getHeight() != 0) {
            return "height:" + Integer.toString(fNode.getHeight()) + ",\n";
        }
        return "height:0,\n";
    }

    public String getWidth(FigmaNode fNode) {
        if (fNode.getWidth() != 0) {
            return "width:" + Integer.toString(fNode.getWidth()) + ",\n";
        }
        return "width:0,\n";
    }
}
