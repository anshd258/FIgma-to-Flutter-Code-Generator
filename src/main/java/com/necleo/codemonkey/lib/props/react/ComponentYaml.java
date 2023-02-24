package com.necleo.codemonkey.lib.props.react;

import com.necleo.codemonkey.factory.YamlPropertySourceFactory;
import com.necleo.codemonkey.lib.props.YamlProp;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties
@PropertySource(value = "classpath:react/component.yml", factory = YamlPropertySourceFactory.class)
public class ComponentYaml extends YamlProp {}
