package com.apirestassured.spotifyapi.model.responseDto;

import com.apirestassured.spotifyapi.model.pojo.ExternalUrls;
import com.apirestassured.spotifyapi.model.pojo.Followers;
import com.apirestassured.spotifyapi.model.pojo.Image;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.Data;

import java.util.List;

@Data
public class GetArtistDto {

    @JsonProperty("external_urls")
    private ExternalUrls externalUrls;

    private Followers followers;

    @Nullable
    private List<String> genres;

    private String href;
    private String id;

    @Nullable
    private List<Image> images;

    private String name;
    private Integer popularity;
    private String type;
    private String uri;
}
