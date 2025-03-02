package com.apirestassured.spotifyapi.model.pojo;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class Followers {

    @Nullable
    private String href;
    private Integer total;
}
