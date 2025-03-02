package com.apirestassured.spotifyapi.apiClient;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import java.util.Base64;


public class ReqSpecification {

    private static final String CLIENT_ID = System.getenv("SPOTIFY_CLIENT_ID");
    private static final String CLIENT_SECRET = System.getenv("SPOTIFY_CLIENT_SECRET");

    private static final String AUTH_URL = "https://accounts.spotify.com/api/token";
    private static final String BASE_API_URL = "https://api.spotify.com/v1";


    // request specifications
    public static RequestSpecification getAuthRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri("https://accounts.spotify.com/api/token")
                .setContentType("application/x-www-form-urlencoded")
                .addHeader("Authorization", "Basic " + getEncodedAuthCredentials())
                .build();
    }

    public static RequestSpecification getApiRequestSpec(String accessToken) {
        return new RequestSpecBuilder()
                .setBaseUri("https://api.spotify.com/v1")
                .setContentType("application/json")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
    }


    // headers
    private static String getEncodedAuthCredentials() {

        String credentials = CLIENT_ID + ":" + CLIENT_SECRET;
        System.out.println("LOGGER - CLIENT_ID: " + CLIENT_ID + ", CLIENT_SECRET: " + CLIENT_SECRET);
        System.out.println("LOGGER - base64: " + Base64.getEncoder().encodeToString(credentials.getBytes()));
        return Base64.getEncoder().encodeToString(credentials.getBytes());
    }
}
