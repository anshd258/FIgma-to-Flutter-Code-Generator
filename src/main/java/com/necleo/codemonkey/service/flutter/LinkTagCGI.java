package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.factory.mapper.FigmaNodeMapper;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.TadDataType;
import com.necleo.codemonkey.lib.types.figma.FigmaRectangleNode;
import com.necleo.codemonkey.lib.types.figma.FigmaTextNode;


public class LinkTagCGI implements  FlutterCGI{

    TextFlutterCGI textFlutterCGI;
    @Override
    public FigmaNodeMapper getEnumMapping() {
        return new FigmaNodeMapper(FigmaNodeTypes.TEXT, TadDataType.LINK);
    }

    @Override
    public String generate(FigmaNode figmaNode) {

            if (!(figmaNode instanceof FigmaTextNode fNode)) {
                throw new IllegalArgumentException();
            }
            return generat(fNode);
        }

    private String generat(FigmaTextNode fNode) {
        final String upperButton = " InkWell(\n";
        final String lowerButton = "),\n";
        String genCode = "";
        genCode += getLink();
        genCode += getChild(fNode);
        return genCode;
    }

    private String getChild(FigmaTextNode fNode) {
        String genChild = "";
        genChild += textFlutterCGI.generate(fNode);
        return "child:" + genChild + ",\n";
    }

    private String getLink() {
        final String upperLink = " onTap: () async { \n";
        final String lowerLink = "},\n";
        String genLink = "";
        genLink += getCheck("test.com");

        return  upperLink + genLink + lowerLink;
    }

    private String getCheck(String url) {
        String genLink = "";
        final String upperUrl = " if (await canLaunch(";
        final String midUrl = ")) {\n";
        final String lowerUrl = "}\n";
            genLink += getLauncher(url);


        return upperUrl + url + midUrl + genLink + lowerUrl;
    }

    private String getLauncher(String url) {
        final String upperLauncher = " await launch(";
        final String lowerLauncher = ");\n";
        return upperLauncher + url +lowerLauncher;
    }

}
