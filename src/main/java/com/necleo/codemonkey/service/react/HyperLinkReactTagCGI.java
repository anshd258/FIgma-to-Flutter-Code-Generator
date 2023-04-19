package com.necleo.codemonkey.service.react;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.TagDataType;
import com.necleo.codemonkey.lib.types.figma.FigmaTextNode;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
@Slf4j

public class HyperLinkReactTagCGI implements ReactCGI{
    TextReactCGI textReactCGI = new TextReactCGI();
//    FigmaNodeMapper figmaNodeMapper = new FigmaNodeMapper();


    @Override
    public String generate(FigmaNode figmaNode) {

        return generat(figmaNode);
    }

    private String generat(FigmaNode fNode) {
        final String upperLink = "<Link\n";

        final String lowerLink = "> "+ getData(fNode) +" </Link>\n";
        String genCode = "";
        genCode += getClickFunction(fNode);
//        genCode += getChild(fNode);
        genCode += getStyles(fNode);
        System.out.println(genCode); // end indent

        return upperLink + genCode + lowerLink;
    }

    public String getData(FigmaNode fNode){
        return fNode.getName();
    }


    private String getClickFunction(FigmaNode fNode) {
        String to = "to='";
        String route = " ";
        return to + route + "'\n";
    }

    private String getStyles(FigmaNode fNode) {
        String styles = "style={{";
        if (fNode.getType().equals(FigmaNodeTypes.TEXT)) {
            styles += textReactCGI.getStyle((FigmaTextNode) fNode);
        }
        return styles + "}}";
    }

    @Override
    public Set<FigmaNodeMapper> getStrategy() {
        return Set.of(new FigmaNodeMapper(FigmaNodeTypes.TEXT, TagDataType.LINK));
    }

}
