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

public class VideoTagCGI implements ReactCGI {

    RectangleReactCGI rectangleReactCGI = new RectangleReactCGI();

    FrameReactCGI frameReactCGI = new FrameReactCGI();

    @Override
    public String generate(FigmaNode figmaNode, Set<String> importsFunctions) {

        return generat(figmaNode);
    }

    private String generat(FigmaNode fNode) {
        final String genCode = "<video controls" + getStyles(fNode) +">" + getVideoSources(fNode) +"<video>\n";
        System.out.println(genCode);
        return genCode ;
    }

    private String getVideoSources(FigmaNode fNode) {
        // while sources array != 0
        // map them ->
        String sources = "";
        // while starts
        String sourceSrc = " ";
        String sourceType = "video/mp4";
        sources += "<source src='" + sourceSrc + "' type='" + sourceType + "' />\n";
        return sources;
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

    @Override
    public Set<FigmaNodeMapper> getStrategy() {
        return Set.of(new FigmaNodeMapper(FigmaNodeTypes.FRAME, TagDataType.VIDEO), new FigmaNodeMapper(FigmaNodeTypes.RECTANGLE, TagDataType.VIDEO));
    }
}