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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FigmaLayersTreeDataConsumer {
  ObjectMapper objectMapper;

  @SqsListener(value = "code-gen-queue")
  public void processMessage(String message) throws JsonProcessingException {
    System.out.println("Received message: " + message);
    // Process the message
    List<FNode> fNodes = objectMapper.readValue(message, new TypeReference<>() {});
    System.out.println(fNodes.get(0).locked());
  }
}
// convert the string to java class then pass it to codegenservice
