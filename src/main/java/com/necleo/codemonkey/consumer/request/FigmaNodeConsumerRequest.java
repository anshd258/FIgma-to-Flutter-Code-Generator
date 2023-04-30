package com.necleo.codemonkey.consumer.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
@Jacksonized
public class FigmaNodeConsumerRequest {

  List<FigmaNode> screen;

  @JsonProperty("tag_data")
  List<TagData> tagData;
}
