 package com.necleo.codemonkey.configuration;
 import com.amazonaws.services.dynamodbv2.xspec.S;
 import com.amazonaws.services.s3.AmazonS3;

 import java.io.BufferedReader;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.InputStreamReader;
 import java.net.URL;
 import java.time.Instant;
 import java.util.*;

 import com.amazonaws.services.s3.model.S3Object;
 import com.amazonaws.services.s3.model.S3ObjectInputStream;
 import com.amazonaws.util.IOUtils;
 import com.amazonaws.util.StringUtils;
 import lombok.AccessLevel;
 import lombok.RequiredArgsConstructor;
 import lombok.experimental.FieldDefaults;
 import lombok.extern.slf4j.Slf4j;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.stereotype.Service;

 @Configuration
 @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
 @RequiredArgsConstructor
 @Service
 @Slf4j
 public class S3FileLoader {

  @Value("${aws.s3.bucket.name}")
  String bucketName;

//  @Value("${aws.s3.base.folder}")
//  String folderName;

  AmazonS3 s3Client;


  public String getJsonData(String bucketName, String key) throws IOException {
   S3Object obj = s3Client.getObject(bucketName, key);
//   String res = IOUtils.toString(obj.getObjectContent());
   String res;
   try {
    res = IOUtils.toString(obj.getObjectContent());
   } finally {
    IOUtils.closeQuietly(obj.getObjectContent(), null);
   }

   return res;
  }

  public String getImageUrl(String imgHash, String projectId){
      log.info(projectId);
      String bucketUrl = "project/"+projectId+"/asset/"+imgHash;
      return s3Client.generatePresignedUrl(bucketName, bucketUrl, Date.from(Instant.now().plusSeconds(10))).toString();
  }


 }
