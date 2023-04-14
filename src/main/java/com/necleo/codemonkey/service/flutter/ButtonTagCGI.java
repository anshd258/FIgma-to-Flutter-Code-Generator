package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.factory.mapper.FigmaNodeMapper;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.TadDataType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
@Slf4j
public class ButtonTagCGI implements FlutterCGI{



    @Override
    public String generate(FigmaNode fNode) {
        return null;
    }

    @Override
    public FigmaNodeMapper getEnumMapping() {
        return new FigmaNodeMapper(FigmaNodeTypes.FRAME,TadDataType.BUTTON);
    }
}
