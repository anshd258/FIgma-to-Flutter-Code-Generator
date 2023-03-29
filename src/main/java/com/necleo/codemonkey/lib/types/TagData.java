package com.necleo.codemonkey.lib.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
@Jacksonized
public class TagData {
  @JsonProperty("tag_name")
  String tagName;

  @JsonProperty("figma_node_id")
  String figmaNodeId;

  @JsonProperty("tag_data")
  Map<String, Object> tagData;
}
