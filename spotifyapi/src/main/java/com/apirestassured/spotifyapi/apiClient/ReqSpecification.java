package com.apirestassured.spotifyapi.apiClient;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import java.util.Base64;


public class ReqSpecification {

    private static final String AUTH_URL_CLIENT_CREDENTIALS_FLOW = "https://accounts.spotify.com/api/token";
    private static final String AUTH_URL_AUTHORIZATION_FLOW = "https://accounts.spotify.com/api/token";
    private static final String BASE_API_URL = "https://api.spotify.com/v1";


    // request specifications
    public static RequestSpecification getAuthRequestSpec_throughClientCredentials() {
        return new RequestSpecBuilder()
                .setBaseUri(AUTH_URL_CLIENT_CREDENTIALS_FLOW)
                .setContentType("application/x-www-form-urlencoded")
                .addHeader("Authorization", "Basic " + getEncodedAuthCredentials())
                .build();
    }

    public static RequestSpecification getAuthRequestSpec_throughAuthorization() {
        return new RequestSpecBuilder()
                .setBaseUri(AUTH_URL_AUTHORIZATION_FLOW)
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
