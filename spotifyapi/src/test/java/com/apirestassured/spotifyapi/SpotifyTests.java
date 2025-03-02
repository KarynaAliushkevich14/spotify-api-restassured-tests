package com.apirestassured.spotifyapi;

import com.apirestassured.spotifyapi.apiClient.ApiClient;
import com.apirestassured.spotifyapi.apiClient.AuthorizationTokenGenerator;
import com.apirestassured.spotifyapi.model.responseDto.GetArtistDto;
import com.apirestassured.spotifyapi.model.responseDto.SaveAlbumForCurrentUserResponseDto;
import com.apirestassured.spotifyapi.service.SaveAlbumsForCurrentUserRequestService;
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
    private final SaveAlbumsForCurrentUserRequestService saveAlbumsForCurrentUserRequestService;

    private static final Logger logger = LoggerFactory.getLogger(SpotifyTests.class);

    @Autowired
    public SpotifyTests(ApiClient apiClient, SaveAlbumsForCurrentUserRequestService saveAlbumsForCurrentUserRequestService) {
        this.apiClient = apiClient;
        this.saveAlbumsForCurrentUserRequestService = saveAlbumsForCurrentUserRequestService;
    }

    @Test
    public void getValidToken() {
        Assertions.assertNotNull(AuthorizationTokenGenerator.generateValidAccessToken_clientCredentialsFlow(), "ACCESS TOKEN IS NULL");
    }

    /** https://developer.spotify.com/documentation/web-api/reference/get-an-artist */
    @Test
    public void getArtist() {
        GetArtistDto getArtistDto = apiClient.sendGenericRequest_ClientCredentialsFlow("/artists/0TnOYISbd1XYRBk9myaseg", Method.GET, null, GetArtistDto.class);

        Assertions.assertEquals(getArtistDto.getExternalUrls().getSpotify(), "https://open.spotify.com/artist/0TnOYISbd1XYRBk9myaseg");
        Assertions.assertEquals(getArtistDto.getName(), "Pitbull");
    }

    @Test
    public void saveAlbumsForCurrentUser() {
        var requestBuilder = saveAlbumsForCurrentUserRequestService.saveAlbumsForCurrentUserRequestBuilder;
        var jsonBuilder = saveAlbumsForCurrentUserRequestService.mapObjectToJsonRequest(requestBuilder); // serialize from Request Object to JSON file

        System.out.println(jsonBuilder);
        System.out.println("JSON Body: " + jsonBuilder);

        apiClient.sendGenericRequest_ClientCredentialsFlow(saveAlbumsForCurrentUserRequestService.endpoint, Method.PUT, jsonBuilder, SaveAlbumForCurrentUserResponseDto.class);
    }
}