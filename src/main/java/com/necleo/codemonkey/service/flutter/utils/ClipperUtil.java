package com.necleo.codemonkey.service.flutter.utils;

import com.necleo.codemonkey.configuration.S3FileLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ClipperUtil {

    S3FileLoader s3FileLoader;
    public void getClipperPath() {

        final String upperClipper = """
                import 'package:flutter/material.dart';
                import 'package:svg_path_parser/svg_path_parser.dart';
                
                class MyClipper extends CustomClipper<Path> {
                  final String pathData;

                  MyClipper(
                      {required this.pathData});
                """;
        final String lowerClipper =
                """
                          @override
                          Path getClip(Size size) {
                            Path path = parseSvgPath(pathData);
                          \s
                            return path;
                          }

                          @override
                          bool shouldReclip(CustomClipper<Path> oldClipper) => false;
                        }""";
        s3FileLoader.uploadFile(upperClipper + lowerClipper,"dart", "myclipper", "/project/lib/Utils");

    }
}
