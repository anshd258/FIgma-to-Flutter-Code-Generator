package com.necleo.codemonkey.service.flutter.pubhandler;


import com.necleo.codemonkey.configuration.S3FileLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
@Service
@Slf4j
public class PubspecHandler {


    final String upperPub =

            """

                    publish_to: 'none'\s


                    version: 1.0.0+1

                    environment:
                      sdk: '>=2.19.1 <3.0.0'


                    dependencies:
                      flutter:
                        sdk: flutter

                    """;
    final  String lowerPub = """
              cupertino_icons: ^1.0.2

            dev_dependencies:
              flutter_test:
                sdk: flutter

            \s
              flutter_lints: ^2.0.0




            flutter:

            \s
              uses-material-design: true

            \s
            """;


    public String createPubspec(Set<String> packages, String name , String desc){
        if(packages ==  null){
            packages = new HashSet<String>();
            packages.add("");
        }
     String genPackages = "";
        String genName = "name: " + name + "\n";
        String genDesc = "description: " + desc + "\n";

    genPackages += packages.stream().map( s -> switch (s) {
         case "GOOGLE_FONTS" -> "\tgoogle_fonts: ^4.0.4";
         case "SVG_PATH_PARSER" -> "\tsvg_path_parser: ^1.1.1";
        default -> "";

     }).collect(Collectors.joining());

    return genName + genDesc + upperPub + genPackages + lowerPub;

    }
}
