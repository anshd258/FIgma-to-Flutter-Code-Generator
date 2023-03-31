package com.necleo.codemonkey.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.necleo.codemonkey.consumer.request.FigmaNodeConsumerRequest;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.service.CodeGenService;
import com.necleo.codemonkey.service.FlutterCodeGenImpl;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class FigmaLayersTreeDataConsumer {
  ObjectMapper objectMapper;
  FlutterCodeGenImpl flutterCodeGenImp;
  CodeGenService codeGenService;

  @SqsListener(
      value = "${cloud.aws.sqs.codeGenQueueName}",
      deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
  public void processMessage(Message<String> message) throws JsonProcessingException {
    log.debug("Received message {}", message);
    // Process the message
    String projectId = message.getHeaders().get("ProjectId", String.class);
    FigmaNodeConsumerRequest figmaNodes =
        objectMapper.readValue(message.getPayload(), new TypeReference<>() {});

    Map<String, TagData> tagDataMap =
        figmaNodes.getTagData().stream()
            .collect(Collectors.toMap(TagData::getFigmaNodeId, tagData -> tagData));
    ;
    codeGenService.gen(figmaNodes.getScreen().get(0), tagDataMap);
    log.info(projectId);
  }
}
// convert the string to java class then pass it to codegenservice
