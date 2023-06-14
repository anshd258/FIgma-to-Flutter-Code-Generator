package com.necleo.codemonkey.service.flutter.componenthandler;

import com.necleo.codemonkey.flutter.index.FlutterGI;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import com.necleo.codemonkey.model.factory.FlutterWI;
import com.necleo.codemonkey.service.flutter.FlutterCGI;

import java.util.Set;

public class ComponentSet implements FlutterCGI {
    @Override
    public Set<FigmaNodeMapper> getStrategy() {
        return Set.of(new FigmaNodeMapper(FigmaNodeTypes.COMPONENT_SET,null));
    }

    @Override
    public String generate(FigmaNode figmaNode, FigmaNode parentFigmaNode, FlutterGI flutterGI, FlutterWI flutterWI) {
        return null;
    }

    @Override
    public String generate(FlutterWI fultterNecleoDataNode, FigmaNode figmaNode) {
        return null;
    }
}
