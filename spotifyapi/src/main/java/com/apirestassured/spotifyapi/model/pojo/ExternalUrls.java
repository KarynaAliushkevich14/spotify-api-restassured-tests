package com.apirestassured.spotifyapi.model.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExternalUrls{
    @JsonProperty("spotify")
    private String spotify;
}
