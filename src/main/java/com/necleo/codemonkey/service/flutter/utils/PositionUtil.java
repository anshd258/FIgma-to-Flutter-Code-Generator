package com.necleo.codemonkey.service.flutter.utils;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.figma.properties.Constrains;
import com.necleo.codemonkey.model.factory.NecleoDataNode;

public class PositionUtil {

  public String getPosition(String genCode, FigmaNode fNode, FigmaNode ParentNode, NecleoDataNode necleoDataNode) {
    final String upperPosition = "  Positioned(";
    final String lowerPosition = "),\n";
    String constrain1 = "";
    String constrain2 = "";
    if(fNode.getConstraints() != null){
      switch(fNode.getConstraints().getHorizontal()){

        case MAX ->  constrain1 = "right" + ( ParentNode.getWidth() - (fNode.getWidth() + fNode.getX())) + ",\n";
        case MIN ->   constrain1 = "left:" + fNode.getX() + ",\n";
        case SCALE ->  constrain1 = "left:" + (((double) fNode.getX() / necleoDataNode.mainScreen.getWidth())*100) + ".w,\n";
        case CENTER -> constrain1 = "";
        default -> {

          constrain1 = "left:" + fNode.getX() + ",\n";
        }

      }
      switch(fNode.getConstraints().getVertical()){

        case MAX ->  constrain2 = "bottom:" + ( ParentNode.getHeight() - (fNode.getHeight() + fNode.getY())) + ",\n";
        case MIN ->    constrain2 = "top:" + fNode.getY() + ",\n";
        case SCALE ->  constrain2 = "top:" + (((double) fNode.getY() / necleoDataNode.mainScreen.getHeight())*100) + ".h,\n";
        case CENTER-> constrain2 = "";
        default -> {

          constrain2 = "top:" + fNode.getY() + ",\n";
        }

      }
    }else{
      constrain1 = "left:" + fNode.getX() + ",\n";
      constrain2 = "top:" + fNode.getY() + ",\n";
    }



    String child = "child:" + genCode + "\n";
    return upperPosition + constrain1 + constrain2 + child  + lowerPosition;
  }
}
