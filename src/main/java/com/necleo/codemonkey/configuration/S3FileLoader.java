 package com.necleo.codemonkey.configuration;
 import com.amazonaws.services.s3.AmazonS3;

 import com.amazonaws.services.s3.model.*;
 import com.amazonaws.util.IOUtils;
 import lombok.AccessLevel;
 import lombok.RequiredArgsConstructor;
 import lombok.experimental.FieldDefaults;
 import lombok.extern.slf4j.Slf4j;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.stereotype.Service;

 import java.io.IOException;
 import java.net.URL;
 import java.nio.charset.StandardCharsets;
 import java.util.Date;
 import java.util.List;

 @Configuration
 @Service
 @Slf4j
 @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
 @RequiredArgsConstructor
 public class S3FileLoader {

  @Value("${aws.s3.bucket.name}")
  String bucketName;

//make this to get images
 @Autowired
  AmazonS3 s3Client;

 public URL getImageLink(String imgHash, String projectId){

  String bucketUrl = "project/"+projectId+"/asset/"+imgHash;
  var url =   s3Client.generatePresignedUrl(bucketName,bucketUrl,new Date(System.currentTimeMillis() + 3600000));
 return url;
 }
  public ObjectMetadata getImageMetadata(String imgHash, String projectId) {
   String bucketUrl = "project/"+projectId+"/asset/"+imgHash;
   return s3Client.getObjectMetadata(bucketName, bucketUrl);
  }


 }
