package com.necleo.codemonkey.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.necleo.codemonkey.consumer.request.FigmaNodeConsumerRequest;
import com.necleo.codemonkey.service.CodeGenService;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class FigmaLayersTreeDataConsumer {
  ObjectMapper objectMapper;

  CodeGenService codeGenService;

  @SqsListener(
      value = "${cloud.aws.sqs.codeGenQueueName}",
      deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
  public void processMessage(Message<String> message) throws JsonProcessingException {
    log.debug("Received message {}", message);
    System.out.println(message);
    // Process the message
    String projectId = message.getHeaders().get("ProjectId", String.class);
    FigmaNodeConsumerRequest figmaNodes =
        objectMapper.readValue(message.getPayload(), new TypeReference<>() {});
    codeGenService.gen(figmaNodes.getScreen());
    log.info(projectId);
  }
}
// convert the string to java class then pass it to codegenservice
