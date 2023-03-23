package com.necleo.codemonkey.configuration.AwsConfig;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AwsConfig {

  @Value("${cloud.aws.credentials.accessKey}")
  String accessKeyId;

  @Value("${cloud.aws.credentials.secretKey}")
  String secretKey;

  @Value("${cloud.aws.region.static}")
  String region;

  @Bean
  BasicAWSCredentials basicAWSCredentials() {
    return new BasicAWSCredentials(accessKeyId, secretKey);
  }

  @Primary
  @Bean
  AmazonSQSAsync amazonSQSAsync() {
    return AmazonSQSAsyncClientBuilder.standard()
        .withRegion(region)
        .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials()))
        .build();
  }
}
