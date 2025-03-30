package com.apirestassured.spotifyapi.apiClient;

import lombok.Getter;


public class SpotifyClientService {

    @Getter
    private final static String clientId = System.getenv("SPOTIFY_CLIENT_ID");
    @Getter
    private final static String clientSecret = System.getenv("SPOTIFY_CLIENT_SECRET");
    @Getter
    private static final String redirectUri = System.getenv("SPOTIFY_REDIRECT_URI"); // http://localhost:8080/callback

    @Getter
    private static final String login = System.getenv("SPOTIFY_LOGIN");
    @Getter
    private static final String password = System.getenv("SPOTIFY_PASSWORD");
}
