package com.necleo.codemonkey.lib.props.html;

import com.necleo.codemonkey.factory.YamlPropertySourceFactory;
import com.necleo.codemonkey.lib.props.YamlProp;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@EqualsAndHashCode(callSuper = false)
@Configuration
@ConfigurationProperties
@PropertySource(value = "classpath:html/main.yml", factory = YamlPropertySourceFactory.class)
public class MainHtmlYaml extends YamlProp {}
