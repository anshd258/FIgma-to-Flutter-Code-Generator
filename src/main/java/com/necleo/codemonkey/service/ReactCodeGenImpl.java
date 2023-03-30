package com.necleo.codemonkey.service;

import com.necleo.codemonkey.enums.Language;
import com.necleo.codemonkey.lib.types.ASTNode;
import com.necleo.codemonkey.lib.types.FNode;

import java.util.Objects;
public class ReactCodeGenImpl implements CodeGen {

    @Override
        public Language getLanguage() {
            return Language.REACT;
        }

        @Override
        public ASTNode generate(FNode fNode) {
            if(Objects.equals(fNode.getType(), "RECTANGLE")) {
            }
            return null;
        }
}


