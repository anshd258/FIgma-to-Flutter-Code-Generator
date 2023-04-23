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
public class CheckBoxTagCGI implements ReactCGI {
    RectangleReactCGI rectangleReactCGI = new RectangleReactCGI();

    @Override
    public String generate(FigmaNode figmaNode, Set<String> importsFunctions) {

        return generat(figmaNode, importsFunctions);
    }

    private String generat(FigmaNode fNode,  Set<String> importsFunctions) {
        final String State = """
                const [check, setChecked] = React.useState(false)
                const handleChange = () => {
                    setChecked(!check);
                  };""";

        importsFunctions.add(State);
        final String upperLink = "<label>" + getLabel(fNode) +"<input\n";
        final String lowerLink = "/></label>\n";
        // if input type = submit - return submitInput
        String genCode = "";
        genCode += getInputName(fNode);
        genCode += getInputType(fNode, "checkbox");
        genCode += getStyles(fNode);
        System.out.println(genCode); // end indent


        return upperLink + genCode + lowerLink;
    }

    private String getInputName(FigmaNode fNode) {
        return "name='test'\n";
    }

    public String getInputType(FigmaNode figmaNode, String type){
        return "type='" + type + "'\n" + "checked={check}\n" + "onChange={handleChange}";
    }

    private String getLabel(FigmaNode fNode) {
        return fNode.getName();
    }

    private String getStyles(FigmaNode fNode) {
        String styles = "style={{";

        if (fNode.getType().equals(FigmaNodeTypes.RECTANGLE)){
            styles += rectangleReactCGI.getRectangleStyles(fNode);
        }
        return styles + "}}";
    }

    @Override
    public Set<FigmaNodeMapper> getStrategy() {
        return Set.of(new FigmaNodeMapper(FigmaNodeTypes.FRAME, TagDataType.CHECKBOX), new FigmaNodeMapper(FigmaNodeTypes.RECTANGLE, TagDataType.CHECKBOX));
    }
}
