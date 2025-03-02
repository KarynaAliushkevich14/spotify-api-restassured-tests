package com.apirestassured.spotifyapi.service;

import com.apirestassured.spotifyapi.model.requestBuilder.SaveAlbumsForCurrentUserRequestBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope("prototype")
public class SaveAlbumsForCurrentUserRequestService implements RequestServiceBase{

    public final String endpoint = "/me/albums";

    public SaveAlbumsForCurrentUserRequestBuilder saveAlbumsForCurrentUserRequestBuilder =
            SaveAlbumsForCurrentUserRequestBuilder
                    .builder()
                    .ids(new ArrayList<>(List.of("382ObEPsp2rxGrnsizN5TX", "1A2GTWGtFfWp7KSQTwWOyo", "2noRn2Aes5aoNVsU6iWThc")))
            .build();
}
