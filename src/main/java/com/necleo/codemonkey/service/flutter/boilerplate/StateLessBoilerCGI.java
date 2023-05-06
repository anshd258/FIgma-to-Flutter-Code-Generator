package com.necleo.codemonkey.service.flutter.boilerplate;

import com.necleo.codemonkey.lib.types.enums.boilerplate.BoilerType;
import com.necleo.codemonkey.model.factory.BoilerNodeMapper;
import java.util.Set;

public class StateLessBoilerCGI implements BoilerCGI {
  @Override
  public Set<BoilerNodeMapper> getStrategy() {
    return Set.of(new BoilerNodeMapper(BoilerType.STATELESS, null));
  }

  @Override
  public String generate() {
    return null;
  }
}
