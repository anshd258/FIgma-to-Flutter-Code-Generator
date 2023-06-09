package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.configuration.S3FileLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MainClassMaker {
  S3FileLoader s3FileLoader;

  public String uploadMain(String imports, String home) {
    String upperMainfile =
        """
                void main() => runApp(MyApp());

                class MyApp extends StatelessWidget {
                  @override
                  Widget build(BuildContext context) {
                    return MaterialApp(
                    home:\s
                """;
    String lowerMainfile =
        """
                 ,);
                  }
                }
                """;

    return imports + upperMainfile + home + lowerMainfile;
  }
}
