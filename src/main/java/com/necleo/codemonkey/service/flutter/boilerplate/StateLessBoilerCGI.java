package com.necleo.codemonkey.service.flutter.boilerplate;

import com.necleo.codemonkey.lib.types.enums.boilerplate.BoilerType;
import com.necleo.codemonkey.model.factory.BoilerNodeMapper;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StateLessBoilerCGI implements BoilerCGI {
  @Override
  public Set<BoilerNodeMapper> getStrategy() {
    return Set.of(new BoilerNodeMapper(BoilerType.STATELESS, null));
  }

  @Override
  public String generate(String genCode) {
//    String genWidget = "";
//    genWidget += genCode.substring(0, genCode.length() - 2) + "\n";
    return """

            class MainApp extends StatelessWidget {
              const MainApp({super.key});

              @override
              Widget build(BuildContext context) {
                return  Scaffold(
                   body: %s
                );
              }
            }
            """
            .formatted(genCode);
  }
}
