package com.apirestassured.spotifyapi.service;

import lombok.Getter;


public class SpotifyClientService {

    @Getter
    private final static String clientId = System.getenv("SPOTIFY_CLIENT_ID");
    @Getter
    private final static String clientSecret = System.getenv("SPOTIFY_CLIENT_SECRET");
}
