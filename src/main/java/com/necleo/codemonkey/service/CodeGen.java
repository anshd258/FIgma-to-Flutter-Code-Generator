package com.necleo.codemonkey.service;

import com.necleo.codemonkey.enums.Language;
import com.necleo.codemonkey.lib.types.ASTNode;
import com.necleo.codemonkey.lib.types.FNode;

public interface CodeGen {
    Language getLanguage();

    ASTNode generate(FNode fNode);
}
