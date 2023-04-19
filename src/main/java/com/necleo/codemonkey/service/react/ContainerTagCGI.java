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
public class ContainerTagCGI implements ReactCGI{

    RectangleReactCGI rectangleReactCGI = new RectangleReactCGI();
    FrameReactCGI frameReactCGI = new FrameReactCGI();

    @Override
    public String generate(FigmaNode figmaNode) {

        return generat(figmaNode);
    }

    public String generat(FigmaNode fNode){
        if (fNode.getType().equals(FigmaNodeTypes.RECTANGLE)){
            return rectangleReactCGI.generate(fNode);
        } else {
            return frameReactCGI.generate(fNode);
        }
    }

    @Override
    public Set<FigmaNodeMapper> getStrategy() {
        return Set.of(new FigmaNodeMapper(FigmaNodeTypes.FRAME, TagDataType.CONTAINER), new FigmaNodeMapper(FigmaNodeTypes.RECTANGLE, TagDataType.CONTAINER));
    }
}
