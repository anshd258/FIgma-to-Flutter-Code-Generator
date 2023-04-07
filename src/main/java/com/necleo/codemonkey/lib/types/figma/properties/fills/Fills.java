package com.necleo.codemonkey.lib.types.figma.properties.fills;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.necleo.codemonkey.lib.types.figma.*;
import com.necleo.codemonkey.lib.types.figma.properties.fills.subtypes.FillsImage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
  @JsonSubTypes.Type(value = FillsImage.class, name = "IMAGE"),
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Jacksonized
public class Fills {
  public Fills() {}

  public String type;
  public boolean visible;
  public int opacity;
  public String BlendMode;
}
