package com.necleo.codemonkey.lib.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.necleo.codemonkey.lib.types.Tagdata.Tag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@SuperBuilder
@NoArgsConstructor
@Jacksonized
@ToString
public class TagData {
  @JsonProperty("tag_name")
  String tagName;

  @JsonProperty("figma_node_id")
  String figmaNodeId;

  @JsonProperty("tag_data")
  Tag tagData;
}
