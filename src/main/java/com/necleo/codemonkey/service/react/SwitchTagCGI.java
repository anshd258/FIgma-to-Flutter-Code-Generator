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
public class SwitchTagCGI implements ReactCGI{
    @Override
    public String generate(FigmaNode figmaNode, Set<String> importsFunctions) {

        return generat(figmaNode);
    }

    private String generat(FigmaNode figmaNode) {
        return "";
    }

    @Override
    public Set<FigmaNodeMapper> getStrategy() {
        return Set.of(new FigmaNodeMapper(FigmaNodeTypes.RECTANGLE, TagDataType.SWITCH));
    }

}
