package com.necleo.codemonkey.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.necleo.codemonkey.lib.types.FNode;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class FigmaLayersTreeDataConsumer {
  ObjectMapper objectMapper;

  @SqsListener(value = "code-gen-queue")
  public void processMessage(String message) throws JsonProcessingException {
    log.debug("Received message: " + message);
    // Process the message
    List<FNode> fNodes = objectMapper.readValue(message, new TypeReference<>() {});
  }
}
// convert the string to java class then pass it to codegenservice
