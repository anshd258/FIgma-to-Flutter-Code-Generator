package com.necleo.codemonkey.service.flutter.utils;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.model.factory.NecleoDataNode;

public class SizeUtil {
  public String getHeight(FigmaNode fNode, FigmaNode mainScreen, NecleoDataNode necleoDataNode ) {
    if(fNode.getConstrains() != null){
      switch (fNode.getConstrains().getVertical()) {
        case STRETCH, SCALE -> {
          necleoDataNode.responsive = true;
          return "height:" + ((fNode.getHeight() / mainScreen.getHeight()) * 100) + ".h,\n";
        }
        default -> {
          return "height:" + Integer.toString(fNode.getHeight()) + ",\n";
        }
      }

    }else{
      return "height:" + Integer.toString(fNode.getHeight()) + ",\n";
    }




  }

  public String getWidth(FigmaNode fNode, FigmaNode mainScreen , NecleoDataNode necleoDataNode) {
    if(fNode.getConstrains() != null)
    {
      switch (fNode.getConstrains().getHorizontal()) {
        case STRETCH, SCALE -> {
          necleoDataNode.responsive = true;
          return "width:" + ((fNode.getWidth() / mainScreen.getWidth()) * 100) + ".w,\n";
        }
        default -> {
          return "width:" + Integer.toString(fNode.getWidth()) + ",\n";
        }
      }
    }else{
      return "width:" + Integer.toString(fNode.getWidth()) + ",\n";}
  }


}
