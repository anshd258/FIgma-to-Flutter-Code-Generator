package com.necleo.codemonkey.service.flutter.boilerplate;

import com.necleo.codemonkey.factory.Factory;
import com.necleo.codemonkey.lib.types.enums.boilerplate.BoilerType;
import com.necleo.codemonkey.model.factory.BoilerNodeMapper;

import java.util.Set;

public interface BoilerCGI extends Factory<Set<BoilerNodeMapper>> {
    String generate();
}
