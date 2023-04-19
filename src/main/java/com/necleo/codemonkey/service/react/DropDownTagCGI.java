package com.necleo.codemonkey.service.react;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.TagDataType;
import com.necleo.codemonkey.lib.types.figma.FigmaRectangleNode;
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
    public String generate(FigmaNode figmaNode) {

        return generat(figmaNode);
    }

    private String generat(FigmaNode fNode) {
        // this will go outside the return Block
        final String StateDropDown = """
                const [open, setOpen] = React.useState(false);
                const handleOpen = () => {
                    setOpen(!open);
                  };""";
        final String genCode = "<button onClick={handleOpen}" + getStyles(fNode) +">" + getChildDropDown(fNode) +"</button>\n";
        System.out.println(genCode);
        return genCode ;
    }

    private String getChildDropDown(FigmaNode fNode) {
        String menu = "";
        String generatedMenu = rectangleReactCGI.generate(fNode);
        menu += "{open ? " + generatedMenu + ": "+ generatedMenu.replaceAll("visibility: true", "visibility: false") +"}";
        return menu;
    }


    private String getStyles(FigmaNode fNode) {
        String styles = "styles={{";

        if (fNode.getType().equals(FigmaNodeTypes.RECTANGLE)){
            styles += rectangleReactCGI.getImageStyles((FigmaRectangleNode) fNode);
        }
        return styles + "}}";
    }

    @Override
    public Set<FigmaNodeMapper> getStrategy() {
        return Set.of(new FigmaNodeMapper(FigmaNodeTypes.FRAME, TagDataType.DROPDOWN), new FigmaNodeMapper(FigmaNodeTypes.RECTANGLE, TagDataType.DROPDOWN));
    }
}
