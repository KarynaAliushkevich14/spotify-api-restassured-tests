package com.apirestassured.spotifyapi.apiClient;

import com.apirestassured.spotifyapi.service.SpotifyClientService;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import java.util.Base64;


public class ReqSpecification {

    private static final String AUTH_URL = "https://accounts.spotify.com/api/token";
    private static final String BASE_API_URL = "https://api.spotify.com/v1";


    // request specifications
    public static RequestSpecification getAuthRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(AUTH_URL)
                .setContentType("application/x-www-form-urlencoded")
                .addHeader("Authorization", "Basic " + getEncodedAuthCredentials())
                .build();
    }

    public static RequestSpecification getApiRequestSpec(String accessToken) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_API_URL)
                .setContentType("application/json")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
    }

    // headers
    private static String getEncodedAuthCredentials() {
        String credentials = SpotifyClientService.getClientId() + ":" + SpotifyClientService.getClientSecret();

        return Base64.getEncoder().encodeToString(credentials.getBytes());
    }
}
