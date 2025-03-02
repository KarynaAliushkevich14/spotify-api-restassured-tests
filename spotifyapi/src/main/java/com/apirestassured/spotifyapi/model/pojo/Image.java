package com.apirestassured.spotifyapi.model.pojo;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class Image {
    private String url;
    @Nullable
    private Integer height;
    @Nullable
    private Integer width;
}
