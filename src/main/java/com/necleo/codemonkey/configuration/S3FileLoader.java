// package com.necleo.codemonkey.configuration;
//
// import com.amazonaws.services.s3.AmazonS3;
// import com.amazonaws.services.s3.model.*;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
// import com.necleo.codemonkey.lib.props.YamlProp;
// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.InputStream;
// import java.io.InputStreamReader;
// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import lombok.AccessLevel;
// import lombok.RequiredArgsConstructor;
// import lombok.experimental.FieldDefaults;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
//
// @Configuration
// @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
// @RequiredArgsConstructor
// public class S3FileLoader {
//
//  @Value("${aws.s3.bucket.name}")
//  String bucketName;
//
//  @Value("${aws.s3.base.folder}")
//  String folderName;
//
//  AmazonS3 s3Client;
//
//  @Bean
//  public Map<String, List<YamlProp>> loadS3Files() throws IOException {
//    Map<String, List<YamlProp>> folderToObjectsMap = new HashMap<>();
//
//    ListObjectsRequest listObjectsRequest =
//        new ListObjectsRequest().withBucketName(bucketName).withPrefix(folderName + "/");
//    ObjectListing objectListing;
//    do {
//      objectListing = s3Client.listObjects(listObjectsRequest);
//      for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
//        String key = objectSummary.getKey();
//        if (key.equals(folderName + "/")) continue;
//        S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucketName, key));
//        InputStream inputStream = s3Object.getObjectContent();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//        StringBuilder stringBuilder = new StringBuilder();
//        String line;
//        while ((line = reader.readLine()) != null) {
//          stringBuilder.append(line);
//        }
//        if (stringBuilder.isEmpty()) continue;
//        ObjectMapper objectMapper = new YAMLMapper();
//        YamlProp myObject = objectMapper.readValue(stringBuilder.toString(), YamlProp.class);
//        if (!folderToObjectsMap.containsKey(folderName)) {
//          folderToObjectsMap.put(folderName, new ArrayList<>());
//        }
//        folderToObjectsMap.get(folderName).add(myObject);
//      }
//      listObjectsRequest.setMarker(objectListing.getNextMarker());
//    } while (objectListing.isTruncated());
//
//    return folderToObjectsMap;
//  }
// }
