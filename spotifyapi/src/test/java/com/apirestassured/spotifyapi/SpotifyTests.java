package com.apirestassured.spotifyapi;

import com.apirestassured.spotifyapi.apiClient.ApiClient;
import com.apirestassured.spotifyapi.apiClient.AuthorizationTokenGenerator;
import com.apirestassured.spotifyapi.apiClient.RequestAuthorizationCode;
import com.apirestassured.spotifyapi.model.responseDto.GetArtistDto;
import com.apirestassured.spotifyapi.model.responseDto.SaveAlbumForCurrentUserResponseDto;
import com.apirestassured.spotifyapi.selenium.SpotifyLoginSelenium;
import com.apirestassured.spotifyapi.selenium.WebElementHelper;
import com.apirestassured.spotifyapi.service.SaveAlbumsForCurrentUserRequestService;
import io.restassured.http.Method;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // allows to use non-static @BeforeAll
@SpringBootTest(classes = SpotifyApiApplication.class)
public class SpotifyTests {

    @Autowired
    private ApiClient apiClient;
    @Autowired
    private SaveAlbumsForCurrentUserRequestService saveAlbumsForCurrentUserRequestService;
    @Autowired
    private RequestAuthorizationCode requestAuthorizationCode;

    private WebDriver driver;

    private SpotifyLoginSelenium spotifyLoginSelenium;

    private static final Logger logger = LoggerFactory.getLogger(SpotifyTests.class);


    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        spotifyLoginSelenium = new SpotifyLoginSelenium(driver);
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
        String url = requestAuthorizationCode.getRequestAuthorizationCode();
        // selenium
        String code = spotifyLoginSelenium.getCodeAfterRedirect(driver, url);

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

    @AfterEach
    public void tearDown() {
        // create new chromeDriver
        if (driver != null) {
            driver = null;
            driver.quit();
        }
    }
}