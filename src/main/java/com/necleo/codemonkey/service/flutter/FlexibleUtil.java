package com.necleo.codemonkey.service.flutter;

public class FlexibleUtil {
    public String getFlexible(String genCode){
        String upperFlexible = "Flexible(\n" +
                "                      flex: 1,\n" +
                "                      fit: FlexFit.loose,\n" +
                "                      child:";
        String lowerFlexible = "),\n";
        return upperFlexible + genCode + lowerFlexible;
    }
}
