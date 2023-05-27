package com.necleo.codemonkey.model.factory;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;

import java.util.Map;
import java.util.Set;

// @Builder

public class NecleoDataNode {

  public FigmaNode fNode;
  public Boolean  responsive ;
  public FigmaNode mainScreen;

  public TagData tagData;
  public Set<String> imports;
  public Set<String> packages;
  public Set<String> assets;
  public Map<String,String> functionData;

  public Map<String,String> utilClass;

  public Map<String,String> screenData;

}
