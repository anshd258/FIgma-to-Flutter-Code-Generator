//package com.necleo.codemonkey.lib.utils;
//
//javafx.util.Pair;
//
//import java.util.List;
//

//import org.springframework.stereotype.Component;
//
//@Component
//public class ConvertJsxCssToBasicCss {
//    public String convertJSXStyleToCSS(List<Pair<String, String>> jsxStyle) {
//        StringBuilder css = new StringBuilder();
//
//        for (Pair<String, String> pair : jsxStyle) {
//            String property = pair.getKey();
//            String value = pair.getValue();
//
//            if (property != null && value != null) {
//                // Convert camelCase to kebab-case
//                String kebabCaseProperty = property.replaceAll("([a-z])([A-Z])", "$1-$2").toLowerCase();
//                css.append(kebabCaseProperty).append(": ").append(value).append(";");
//            }
//        }
//
//        return css.toString();
//    }
//
//}
