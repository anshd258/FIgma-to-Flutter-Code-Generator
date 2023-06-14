package com.necleo.codemonkey.lib.types.figma.properties.vector.regions;

import com.necleo.codemonkey.lib.types.figma.properties.fills.Fills;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegionType {
  private String windingRule;
  private ArrayList<ArrayList<Number>> loop;

  private ArrayList<Fills> fills;
  private String fillsStyleId;
}
