package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.TagDataType;
import com.necleo.codemonkey.lib.types.figma.FigmaRectangleNode;
import com.necleo.codemonkey.lib.types.figma.properties.fills.subtypes.FillsSolid;
import com.necleo.codemonkey.lib.types.figma.properties.strokes.Strokes;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
@Slf4j
public class SliderTagCGI implements FlutterCGI{
    @Override
    public Set<FigmaNodeMapper> getStrategy() {
        return Set.of(new FigmaNodeMapper(FigmaNodeTypes.RECTANGLE, TagDataType.SLIDER));
    }

    @Override
    public String generate(FigmaNode figmaNode, TagData tagData) {
        if (!(figmaNode instanceof FigmaRectangleNode fNode)) {
            throw new IllegalArgumentException();
        }
        return generat(fNode);
    }

    private String generat(FigmaRectangleNode fNode) {
        final String upperBoxing  = "SizedBox(";
        final String lowerBoxing  = ")";
        String genCode = "";
        genCode += getSize(fNode);
        genCode += getSlider(fNode);
        return upperBoxing + genCode + lowerBoxing;
    }

    private String getSlider(FigmaRectangleNode fNode) {
        final String upperSlider = "child: Slider(\n";
        final  String lowerSlider = " value: _value,\n" +

                "        min: 0.0,\n" +
                "        max: 100.0,\n" +
                "        onChanged: (newValue) {\n" +
                "          setState(() {\n" +
                "            _value = newValue;\n" +
                "          });\n" +
                "        },),";
        String genCode = "";
        if (fNode.getFills().get(0).getType().equals("SOLID")) {
            final FillsSolid fills = (FillsSolid) fNode.getFills().get(0);
            if (fills.getColor() != null && fNode.getFills().get(0).getOpacity() != 0) {
                genCode += color(fills);
            }
        }
        return upperSlider + genCode + lowerSlider ;
    }
    private String color(FillsSolid fills) {
        final String upperColor = "activeColor: Color.fromRGBO(\n";
        final String lowerColor = "),\n";
        final String red = Math.round(fills.getColor().getR() * 255) + ",";
        final String green = Math.round(fills.getColor().getG() * 255) + ",";
        final String blue = Math.round(fills.getColor().getB() * 255) + ",";
        final String opacity = Float.toString(fills.getOpacity());

        return upperColor + red + green + blue + opacity + lowerColor;
    }
    private String getSize(FigmaRectangleNode fNode) {
        String genSize = "";
        genSize += "width:" + fNode.getWidth() + ",\n";
        genSize += "height:" + fNode.getHeight() + ",\n";
        return genSize;
    }
}
