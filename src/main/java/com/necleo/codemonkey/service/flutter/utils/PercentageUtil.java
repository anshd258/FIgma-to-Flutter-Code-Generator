package com.necleo.codemonkey.service.flutter.utils;

public class PercentageUtil {
  public int getLeft(int x, int parentWidth) {
    return (x / parentWidth) * 100;
  }

  public int getTop(int y, int parentHeight) {
    return (y / parentHeight) * 100;
  }

  public int getRight(int x, int parentWidth, int childWidth) {

    return ((parentWidth - childWidth - x) / parentWidth) * 100;
  }

  public int getBottom(int y, int parentHeight, int childHeight) {

    return ((parentHeight - childHeight - y) / parentHeight) * 100;
  }
}
