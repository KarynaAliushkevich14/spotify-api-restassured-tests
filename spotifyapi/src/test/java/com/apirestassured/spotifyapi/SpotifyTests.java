package com.apirestassured.spotifyapi;

import com.apirestassured.spotifyapi.apiClient.ApiClient;
import com.apirestassured.spotifyapi.apiClient.AuthorizationTokenGenerator;
import com.apirestassured.spotifyapi.model.responseDto.GetArtistDto;
import io.restassured.http.Method;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = SpotifyApiApplication.class)
public class SpotifyTests {

    private final ApiClient apiClient;

    private static final Logger logger = LoggerFactory.getLogger(SpotifyTests.class);

    @Autowired
    public SpotifyTests (ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Test
    public void getValidToken() {
        Assertions.assertNotNull(AuthorizationTokenGenerator.generateValidAccessToken(), "ACCESS TOKEN IS NULL");
    }

    /** https://developer.spotify.com/documentation/web-api/reference/get-an-artist */
    @Test
    public void getArtist() {
        GetArtistDto getArtistDto = apiClient.sendGenericRequest("/artists/0TnOYISbd1XYRBk9myaseg", Method.GET, null, GetArtistDto.class);

        Assertions.assertEquals(getArtistDto.getExternalUrls().getSpotify(), "https://open.spotify.com/artist/0TnOYISbd1XYRBk9myaseg");
        Assertions.assertEquals(getArtistDto.getName(), "Pitbull");
    }
}