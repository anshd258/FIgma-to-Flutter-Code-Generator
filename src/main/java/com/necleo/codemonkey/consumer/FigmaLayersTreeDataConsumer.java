package com.necleo.codemonkey.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.necleo.codemonkey.consumer.request.FigmaNodeConsumerRequest;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.service.CodeGenService;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
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

  @SqsListener(
      value = "${cloud.aws.sqs.codeGenQueueName}",
      deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
  public void processMessage(Message<String> message) throws JsonProcessingException {
    log.debug("Received message {}", message);
    System.out.println(message);
    // Process the message
    String projectId = message.getHeaders().get("ProjectId", String.class);

    Map<String, Object> o = objectMapper.readValue(message.getPayload(), new TypeReference<>() {});

    Map<String, Object> screen =
        new HashMap<>(
            Map.of(
                "screen",
                ((Map<String, Object>) ((List<Object>) o.get("screen")).get(0)).get("selection")));

    screen.put("tag_data", o.get("tag_data"));

    FigmaNodeConsumerRequest figmaNodes =
        objectMapper.convertValue(screen, new TypeReference<>() {});

    if (!ObjectUtils.isEmpty(figmaNodes.getTagData())) {
      Map<String, TagData> tagDataMap =
          figmaNodes.getTagData().stream()
              .collect(Collectors.toMap(TagData::getFigmaNodeId, tagData -> tagData));
      codeGenService.gen(figmaNodes.getScreen().get(0), tagDataMap);
    }
    log.info(projectId);
  }
}
