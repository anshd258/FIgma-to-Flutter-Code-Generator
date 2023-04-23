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
public class DropDownTagCGI implements ReactCGI{
    RectangleReactCGI rectangleReactCGI = new RectangleReactCGI();

    FrameReactCGI frameReactCGI = new FrameReactCGI();

    @Override
    public String generate(FigmaNode figmaNode, Set<String> importsFunctions) {

        return generat(figmaNode);
    }

    private String generat(FigmaNode fNode) {
        // this will go outside the return Block
        String StateDropDown = """
                const [value, setValue] = useState('');
                                
                  const handleChange""";
//        StateDropDown += counter from map;

        StateDropDown += """
                     = (e) => {
                    setValue(e.target.value);
                  };""";
        final String genCode = "<select value={value} onChange={handleChange}\n" +
                 getStyles(fNode) +">" + getChildDropDown(fNode) +"</select>\n";
        System.out.println(genCode);
        return genCode ;
    }

    private String getChildDropDown(FigmaNode fNode) {
        String menu = "<option value=\"Orange\">Orange</option>\n" +
                "        <option value=\"Radish\">Radish</option>\n" +
                "        <option value=\"Cherry\">Cherry</option>";
//        String generatedMenu = rectangleReactCGI.generate(fNode);
//        menu += "{open ? " + generatedMenu + ": "+ generatedMenu.replaceAll("visibility: true", "visibility: false") +"}";
        return menu;
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
        return Set.of(new FigmaNodeMapper(FigmaNodeTypes.FRAME, TagDataType.DROPDOWN), new FigmaNodeMapper(FigmaNodeTypes.RECTANGLE, TagDataType.DROPDOWN));
    }
}
