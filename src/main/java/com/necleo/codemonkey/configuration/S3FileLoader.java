package com.necleo.codemonkey.configuration;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Instant;
import java.util.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import static com.necleo.codemonkey.constant.MDCKey.X_PROJECT_ID;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class S3FileLoader {

  @Value("${aws.s3.bucket.name}")
  String bucketName;
  @Value("${aws.s3.bucket.file}")
  String fileBucketName;

  AmazonS3 s3Client;

  public String getJsonData(String bucketName, String key) throws IOException {
    S3Object obj = s3Client.getObject(bucketName, key);

    String res;
    try {
      res = IOUtils.toString(obj.getObjectContent());
    } finally {
      IOUtils.closeQuietly(obj.getObjectContent(), null);
    }
    return res;
  }

  public URL getImageUrl(String imgHash, String projectId) {
    String bucketUrl = "project/" + projectId + "/asset/" + imgHash;
    return s3Client.generatePresignedUrl(
        bucketName, bucketUrl, Date.from(Instant.now().plusSeconds(3000)));
  }

  public PutObjectResult uploadFile(String content, String extension, String  fileName ,String path){
    String  projectId =   MDC.get(X_PROJECT_ID);

    String fileKey = projectId + path + fileName + "." ;

// Define the file content and extension

// Create the file object with the specified extension
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType("text/plain");
    metadata.setContentLength(content.getBytes().length);
    metadata.setContentDisposition("attachment; filename=\"" + fileKey + extension + "\"");

    ByteArrayInputStream input = new ByteArrayInputStream(content.getBytes());

// Upload the file to S3
    return s3Client.putObject(fileBucketName, fileKey + extension, input, metadata);
  }

  public PutObjectResult getFileAndUpload(String imgHash, String extesion, String name) throws IOException {
    File file = new File("/");
    String  projectId =   MDC.get(X_PROJECT_ID);
    String imgPath = "project/" + projectId + "/asset/" + imgHash;
    S3Object s3Object = s3Client.getObject(bucketName, imgPath);
    InputStream inputStream = s3Object.getObjectContent();
    FileCopyUtils.copy(inputStream.readAllBytes(), file);
    String uploadPath = projectId + "/project/asset/" + name + "." + extesion;
   PutObjectResult result = s3Client.putObject(fileBucketName,uploadPath,file);
   file.delete();
    return result;
  }
}
