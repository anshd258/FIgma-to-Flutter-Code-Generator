package com.necleo.codemonkey.service.flutter.utils;

public class PercentageUtil {
  public float getLeft(int x, int parentWidth) {
    return ((float) x / parentWidth) * 100;
  }

  public float getTop(int y, int parentHeight) {
    return ((float) y / parentHeight) * 100;
  }

  public float getRight(int x, int parentWidth, int childWidth) {

    return ((float) (parentWidth - childWidth - x) / parentWidth) * 100;
  }

  public float getBottom(int y, int parentHeight, int childHeight) {

    return ((float) (parentHeight - childHeight - y) / parentHeight) * 100;
  }
}
