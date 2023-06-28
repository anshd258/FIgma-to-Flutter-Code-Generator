package com.necleo.codemonkey.consumer;

import static com.necleo.codemonkey.constant.MDCKey.X_PROJECT_ID;

import com.amazonaws.services.s3.event.S3EventNotification;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.necleo.codemonkey.configuration.S3FileLoader;
import com.necleo.codemonkey.consumer.request.FigmaNodeConsumerRequest;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.service.CodeGenService;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
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

  /**
   * The message is converted from String to an Object of S3 Event Notification Class. <br>
   * Then List of records are extracted and for each record the {@link #handleRecord} function is
   * called.
   */
  @SqsListener(
      value = "${cloud.aws.sqs.codeGenQueueName}",
      deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
  @SneakyThrows
  public void processMessage(Message<String> message) {

    S3EventNotification s3EventNotification =
        objectMapper.readValue(message.getPayload(), S3EventNotification.class);

    List<S3EventNotification.S3EventNotificationRecord> records = s3EventNotification.getRecords();
    // todo: to think better approch
    records.forEach(this::handleRecord);
  }

  /**
   * We extract the bucket name and key from the record object, and then extract the project id from
   * the key. <br>
   * We map the project id to "X_PROJECT_ID" key in MDC context. <br>
   * We then try to run the method {@link #loadS3AndConsume(String, String)} and after that finally
   * remove the key "X_PROJECT_ID" from the MDC context.
   */
  @SneakyThrows
  private void handleRecord(S3EventNotification.S3EventNotificationRecord record) {

    String bucketName = record.getS3().getBucket().getName();
    String key = record.getS3().getObject().getKey();

    String projectId = key.substring(8, 44);
    try {
      MDC.put(X_PROJECT_ID, projectId);
      loadS3AndConsume(bucketName, key);
    } finally {
      MDC.remove(X_PROJECT_ID);
    }
  }

  /**
   * We get the JSON Data as String and convert it into a Map named "o". <br>
   * A Map named screen is created which has the list of selected screen data as Map mapped to key
   * "screen" and the key "tag_data" is added with a valued of "tag_data" from the Map o. <br>
   * Now this Map screen is converted to Object of Figma Node Consumer Request Class called
   * FigmaNodes. <br>
   * If the List of Tag Data in FigmaNodes in not empty then a Map with key as Figma Node Id and
   * value as the Map of Tag Data is created called Tag Data Map. <br>
   * Now the generate function from {@link #codeGenService} Class is called which takes the list of
   * screens from FigmaNodes and the Tag Data Map.
   */
  private void loadS3AndConsume(String bucketName, String key) throws IOException {
    String jsonData = s3FileLoader.getJsonData(bucketName, key);
    //    var json = objectMapper.readValue(jsonData, new
    // TypeReference<Map<String,List<Map<String,Object>>>>() {
    //    });
    //   var mainData = objectMapper.convertValue(json.get("screen"), new
    // TypeReference<FigmaNodeConsumerRequest>() {
    //    });
    //   var figmaNodes = FigmaNodeConsumerRequest.builder().screen(mainData).build();
    //    FigmaNodeConsumerRequest figmaNodes =
    //        objectMapper.readValue(, FigmaNodeConsumerRequest.class);
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

    codeGenService.generate(figmaNodes.getScreen(), tagDataMap);
  }
}
