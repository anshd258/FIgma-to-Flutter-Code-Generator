 package com.necleo.codemonkey.configuration;
 import com.amazonaws.services.s3.AmazonS3;

 import java.net.URL;
 import java.time.Instant;
 import java.util.*;
 import lombok.AccessLevel;
 import lombok.RequiredArgsConstructor;
 import lombok.experimental.FieldDefaults;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.stereotype.Service;

 @Configuration
 @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
 @RequiredArgsConstructor
 @Service
 public class S3FileLoader {

  @Value("${aws.s3.bucket.name}")
  String bucketName;

  @Value("${aws.s3.base.folder}")
  String folderName;

  AmazonS3 s3Client;


  public URL getImageUrl(String imgHash, String projectId){
      String bucketUrl = "project/"+projectId+"/asset/asset-"+imgHash;
      return s3Client.generatePresignedUrl(bucketName, bucketUrl, Date.from(Instant.now()));
  }


 }
