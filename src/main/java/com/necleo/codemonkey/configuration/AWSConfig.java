package com.necleo.codemonkey.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AWSConfig {

  @Value("${cloud.aws.credentials.accessKey}")
  String accessKey;

  @Value("${cloud.aws.credentials.secretKey}")
  String secretKey;

  @Bean
  AmazonS3 getAmazonS3() {
    BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
    return AmazonS3ClientBuilder.standard()
        .withRegion(Regions.AP_SOUTH_1)
        .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
        .build();
  }
}
