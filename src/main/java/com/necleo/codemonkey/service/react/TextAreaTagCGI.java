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
public class TextAreaTagCGI implements ReactCGI{
    @Override
    public String generate(FigmaNode figmaNode) {

        return generat(figmaNode);
    }

    private String generat(FigmaNode figmaNode) {
        String genCode = "";
        genCode += "<textarea " + genStyles(figmaNode) + getRowsCols(figmaNode) + getName(figmaNode) + getPlaceholder(figmaNode) + " />";
        return genCode;
    }

    private String getPlaceholder(FigmaNode figmaNode) {
        String placeholder = "";
        placeholder += "test textarea";
        return placeholder;
    }

    private String getRowsCols(FigmaNode figmaNode) {
        if (figmaNode.getHeight() != 0 || figmaNode.getWidth() != 0)
            return "\n";
        return "\n";
    }

    private String genStyles(FigmaNode figmaNode) {
        String styles = "style={{";
        styles += "minHeight: " + figmaNode.getHeight() + ",\n";
        styles += "maxWidth: "+figmaNode.getWidth() + ",\n";
        styles += "resize: 'none'";
        styles += "}}";
        return styles;
    }

    private String getName(FigmaNode figmaNode){
        return "name='" + figmaNode.getName() + "'\n";
    }


    @Override
    public Set<FigmaNodeMapper> getStrategy() {
        return Set.of(new FigmaNodeMapper(FigmaNodeTypes.TEXT, TagDataType.TEXTAREA));
    }
}
