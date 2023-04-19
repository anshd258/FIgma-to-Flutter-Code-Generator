package com.necleo.codemonkey.service.react;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.TagDataType;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
@Slf4j

public class InputReactTagCGI implements ReactCGI {

    RectangleReactCGI rectangleReactCGI = new RectangleReactCGI();

    FrameReactCGI frameReactCGI = new FrameReactCGI();

    @Override
    public String generate(FigmaNode figmaNode) {

        return generat(figmaNode);
    }

    private String generat(FigmaNode fNode) {
        final String upperLink = "<label>" + getLabel(fNode) +"<input\n";
        final String lowerLink = "/></label>\n";
        // if input type = submit - return submitInput
        String submitInput = "<input type='submit' " + getStyles(fNode) + getSubmitValue(fNode) + "/>\n";
        String genCode = "";
        genCode += getLabel(fNode);

        genCode += getStyles(fNode);
        System.out.println(genCode); // end indent


        return upperLink + genCode + lowerLink;
    }

    private String getSubmitValue(FigmaNode fNode) {
        return "Submit";
    }

    private String getLabel(FigmaNode fNode) {
        return fNode.getName();
    }

    private String getStyles(FigmaNode fNode) {
        String styles = "styles={{";

        if (fNode.getType().equals(FigmaNodeTypes.RECTANGLE)){
            styles += rectangleReactCGI.getRectangleStyles(fNode);
        } else if (fNode.getType().equals(FigmaNodeTypes.FRAME)) {
            styles += frameReactCGI.getStyles(fNode);
        }
        return styles + "}}";
    }

    public String getData(FigmaNode fNode){
        return fNode.getName();
    }

    @Override
    public Set<FigmaNodeMapper> getStrategy() {
        return Set.of(new FigmaNodeMapper(FigmaNodeTypes.FRAME, TagDataType.INPUT), new FigmaNodeMapper(FigmaNodeTypes.RECTANGLE, TagDataType.INPUT));
    }
}
