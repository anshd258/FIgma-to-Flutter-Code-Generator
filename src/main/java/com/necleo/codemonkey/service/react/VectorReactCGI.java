package com.necleo.codemonkey.service.react;

import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.figma.FigmaVectorNode;
import com.necleo.codemonkey.lib.types.figma.properties.fills.subtypes.FillsSolid;
import com.necleo.codemonkey.lib.utils.ColorHashGenerator;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VectorReactCGI implements ReactCGI {

    ColorHashGenerator colorHashGenerator;

    @Override
    public Set<FigmaNodeMapper> getStrategy() {
        return Set.of(new FigmaNodeMapper(FigmaNodeTypes.VECTOR, null));
    }

    @Override
    public String generate(FigmaNode figmaNode, FigmaNode node, Map<String, TagData> tagDataMap, Set<String> importsFunctions) {
        if (!(figmaNode instanceof FigmaVectorNode fNode)) {
            throw new IllegalArgumentException();
        }
        return generat(fNode);
    }

    public String generat(FigmaVectorNode fNode) {
        String genCode = "";
        FillsSolid fillsSolid = (FillsSolid) fNode.getFills().get(0);
        String fillColor = "#FF6650";
//                colorHashGenerator.generateColorHash((int) fillsSolid.getColor().getR(), (int) fillsSolid.getColor().getG(), (int) fillsSolid.getColor().getB(), (int) fillsSolid.getOpacity());
        final String begin = "<svg xmlns=\""+ "url" +"\" xmlns:xlink=\""+ "link" +
                "\" aria-hidden=\"true\" role=\"img\" class=\"iconify iconify--logos\" " +
                "width=\""+fNode.getWidth()+"\" height=\""+fNode.getHeight()+"\" preserveAspectRatio=\"xMidYMid meet\" " +
                "viewBox=\"0 0 256 228\"><path fill=\""+fillColor+"\" d=\""+ fNode.getVectorPaths().get(0).getData() +"";
        return genCode;
    }
}
