package com.necleo.codemonkey.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.necleo.codemonkey.lib.types.FNode;
import com.necleo.codemonkey.lib.types.figma.*;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class FigmaLayersTreeDataConsumer {
  ObjectMapper objectMapper;

  @SqsListener(value = "${cloud.aws.sqs.codeGenQueueName}")
  public void processMessage(String message, @Header("ProjectId") String ProjectId)
      throws JsonProcessingException {
    log.debug("Received message {}", message);
    // Process the message
    List<FNode> fNodes = objectMapper.readValue(message, new TypeReference<>() {});
    if (fNodes.get(0).getType().equals("RECTANGLE")) {
      FigmaRectangleNode figmaRectangleNode = (FigmaRectangleNode) fNodes.get(0);
    } else if (fNodes.get(0).getType().equals("ELLIPSE")) {
      FigmaEllipseNode figmaEllipseNode = (FigmaEllipseNode) fNodes.get(0);
    } else if (fNodes.get(0).getType().equals("FRAME")) {
      FigmaFrameNode figmaFrameNode = (FigmaFrameNode) fNodes.get(0);
    } else if (fNodes.get(0).getType().equals("LINE")) {
      FigmaLineNode figmaLineNode = (FigmaLineNode) fNodes.get(0);
      System.out.println(figmaLineNode.height);
    } else if (fNodes.get(0).getType().equals("STAR")) {
      FigmaStarNode figmaStarNode = (FigmaStarNode) fNodes.get(0);
    } else if (fNodes.get(0).getType().equals("POLYGON")) {
      FigmaPolygonNode figmaEllipseNode = (FigmaPolygonNode) fNodes.get(0);
    } else if (fNodes.get(0).getType().equals("VECTOR")) {
      FigmaVectorNode figmaVectorNode = (FigmaVectorNode) fNodes.get(0);
    }
    log.info(ProjectId);
  }
}
// convert the string to java class then pass it to codegenservice
