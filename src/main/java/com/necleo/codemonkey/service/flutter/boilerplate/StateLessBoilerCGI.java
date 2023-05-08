package com.necleo.codemonkey.service.flutter.boilerplate;

import com.necleo.codemonkey.lib.types.enums.boilerplate.BoilerType;
import com.necleo.codemonkey.model.factory.BoilerNodeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
@Slf4j
public class StateLessBoilerCGI implements BoilerCGI {
  @Override
  public Set<BoilerNodeMapper> getStrategy() {
    return Set.of(new BoilerNodeMapper(BoilerType.STATELESS, null));
  }

  @Override
  public String generate(String genCode) {
    String genWidget = "";
    genWidget += getUpperStateLessWidget();
    genWidget += genCode.substring(0,genCode.length()-2) + ";\n";
    genWidget += getLowerStateLessWidget();
    return genWidget;
    
  }

  private String getLowerStateLessWidget() {

    return "  }\n" +
            "}";
  }

  private String getUpperStateLessWidget() {
    return "class MainApp extends StatelessWidget {\n" +
            "  const MainApp({ super.key });\n" +
            "\n" +
            "  @override\n" +
            "  Widget build(BuildContext context) {\n" +
            "    return ";
  }
}
