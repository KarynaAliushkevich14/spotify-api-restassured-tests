package com.apirestassured.spotifyapi;

import com.apirestassured.spotifyapi.apiClient.ApiClient;
import com.apirestassured.spotifyapi.apiClient.AuthorizationTokenGenerator;
import com.apirestassured.spotifyapi.model.responseDto.GetArtistDto;
import com.apirestassured.spotifyapi.model.responseDto.SaveAlbumForCurrentUserResponseDto;
import com.apirestassured.spotifyapi.selenium.SpotifyLoginSelenium;
import com.apirestassured.spotifyapi.service.SaveAlbumsForCurrentUserRequestService;
import io.restassured.http.Method;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = SpotifyApiApplication.class)
public class SpotifyTests {

    private final ApiClient apiClient;
    private final SaveAlbumsForCurrentUserRequestService saveAlbumsForCurrentUserRequestService;
    private final SpotifyLoginSelenium spotifyLoginSelenium;

    private WebDriver driver;

    private static final Logger logger = LoggerFactory.getLogger(SpotifyTests.class);

    @Autowired
    public SpotifyTests(ApiClient apiClient, SaveAlbumsForCurrentUserRequestService saveAlbumsForCurrentUserRequestService, SpotifyLoginSelenium spotifyLoginSelenium) {
        this.apiClient = apiClient;
        this.saveAlbumsForCurrentUserRequestService = saveAlbumsForCurrentUserRequestService;
        this.spotifyLoginSelenium = spotifyLoginSelenium;
    }

    @BeforeEach
    public void createNewWebDriver() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
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
        // make a request through a builder
        var requestBuilder = saveAlbumsForCurrentUserRequestService.saveAlbumsForCurrentUserRequestBuilder;
        var jsonBuilder = saveAlbumsForCurrentUserRequestService.mapObjectToJsonRequest(requestBuilder); // serialize from Request Object to JSON file

        // retrieve a code
        String url = AuthorizationTokenGenerator.getRequestAuthorizationCode();
        String code = spotifyLoginSelenium.getCodeAfterRedirect(url);

        // retrieve accessToken
        String accessToken = AuthorizationTokenGenerator.generateValidAccessToken_authorizationFlow(code);

        System.out.println("JSON Body: " + jsonBuilder);

        // make API call
        apiClient.sendGenericRequest_AuthorizationFlow(
                saveAlbumsForCurrentUserRequestService.endpoint,
                Method.PUT,
                jsonBuilder,
                accessToken,
                SaveAlbumForCurrentUserResponseDto.class);
    }
}