package com.necleo.codemonkey.lib.props.html;

import com.necleo.codemonkey.factory.YamlPropertySourceFactory;
import com.necleo.codemonkey.lib.types.enums.ASTAction;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties
@PropertySource(value = "classpath:html/main.yml", factory = YamlPropertySourceFactory.class)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class MainHtmlYaml {
  String type;
  String value;
  ASTAction action;
  List<MainHtmlYaml> children = new ArrayList<>();
}
