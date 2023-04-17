package com.bda.userservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemeEntity {
    @Getter
    @Setter
    @JsonProperty("id")
    private String id;
    @Getter
    @Setter
    @JsonProperty("about")
    private String about;
    @Getter
    @Setter
    @JsonProperty("origin")
    private String origin;
    @Getter
    @Setter
    @JsonProperty("name")
    private String name;
    @Getter
    @Setter
    @JsonProperty("status")
    private String status;
    @Getter
    @Setter
    @JsonProperty("type")
    private String type;
    @Getter
    @Setter
    @JsonProperty("tags")
    private String tags;
    @Getter
    @Setter
    @JsonProperty("year")
    private String year;
    @Getter
    @Setter
    @JsonProperty("badge")
    private String badge;
    @Getter
    @Setter
    @JsonProperty("gs_public_url")
    private String gsPublicUrl;
}
