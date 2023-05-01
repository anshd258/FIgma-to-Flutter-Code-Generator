package com.necleo.codemonkey.consumer;

import com.amazonaws.services.s3.event.S3EventNotification;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.necleo.codemonkey.configuration.S3FileLoader;
import com.necleo.codemonkey.consumer.request.FigmaNodeConsumerRequest;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.service.CodeGenService;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import java.util.*;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FigmaLayersTreeDataConsumer {
  ObjectMapper objectMapper;

  CodeGenService codeGenService;

  S3FileLoader s3FileLoader;

  @SqsListener(
      value = "${cloud.aws.sqs.codeGenQueueName}",
      deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
  @SneakyThrows
  public void processMessage(Message<String> message) {

    S3EventNotification s3EventNotification = objectMapper.readValue(message.getPayload(), S3EventNotification.class);

    List<S3EventNotification.S3EventNotificationRecord> records = s3EventNotification.getRecords();
    records.forEach(this::handleRecord);

  }

  @SneakyThrows
  private void handleRecord(S3EventNotification.S3EventNotificationRecord record) {

    String bucketName = record.getS3().getBucket().getName();
    String key = record.getS3().getObject().getKey();

    String jsonData = s3FileLoader.getJsonData(bucketName, key);
    log.info(jsonData);

    Map<String, Object> o = objectMapper.readValue(jsonData, new TypeReference<>() {});

    Map<String, Object> screen =
            new HashMap<>(
                    Map.of(
                            "screen",
                            ((Map<String, Object>) ((List<Object>) o.get("screen")).get(0)).get("selection")));

    screen.put("tag_data", o.get("tag_data"));
    FigmaNodeConsumerRequest figmaNodes =
            objectMapper.convertValue(screen, new TypeReference<>() {});

    Map<String, TagData> tagDataMap = new HashMap<>();
    if (!ObjectUtils.isEmpty(figmaNodes.getTagData())) {
      tagDataMap =
              figmaNodes.getTagData().stream()
                      .collect(Collectors.toMap(TagData::getFigmaNodeId, tagData -> tagData));
    }

    codeGenService.gen(figmaNodes.getScreen().get(0), tagDataMap);
  }
}
