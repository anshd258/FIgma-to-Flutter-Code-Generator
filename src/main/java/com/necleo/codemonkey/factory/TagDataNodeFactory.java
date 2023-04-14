package com.necleo.codemonkey.factory;

import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.service.react.ReactCGI;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TagDataNodeFactory extends Factory<FigmaNodeTypes, ReactCGI>{
    @Autowired
    public FigmaNodeFactoryWithTagData(List<ReactCGI> beans) {
        super(beans);
    }
}
