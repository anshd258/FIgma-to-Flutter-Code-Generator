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
public class YoutubeTagCGI implements ReactCGI {
    RectangleReactCGI rectangleReactCGI;

    FrameReactCGI frameReactCGI;

    @Override
    public String generate(FigmaNode figmaNode) {

        return generat(figmaNode);
    }

    private String generat(FigmaNode fNode) {
        final String genCode = "<iframe \n\tallowFullScreen title='Youtube Player'" + getStyles(fNode) +">" + getSrc(fNode) +"<video>\n";
        System.out.println(genCode);
        return genCode ;
    }

    private String getSrc(FigmaNode fNode) {
        // while sources array != 0
        // map them ->
        String src = "";
        return src;
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
        return Set.of(new FigmaNodeMapper(FigmaNodeTypes.FRAME, TagDataType.YOUTUBE), new FigmaNodeMapper(FigmaNodeTypes.RECTANGLE, TagDataType.YOUTUBE));
    }
}
