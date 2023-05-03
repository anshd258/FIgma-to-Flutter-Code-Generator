package com.necleo.codemonkey.configuration;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class S3FileLoader {

  @Value("${aws.s3.bucket.name}")
  String bucketName;

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
}
