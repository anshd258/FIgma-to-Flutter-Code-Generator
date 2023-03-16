package com.necleo.codemonkey;

import io.awspring.cloud.messaging.config.annotation.EnableSqs;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSqs
public class CodeMonkeyApplication {

  public static void main(String[] args) {
    SpringApplication.run(CodeMonkeyApplication.class, args);
  }
}
