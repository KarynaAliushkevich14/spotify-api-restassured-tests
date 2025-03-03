package com.apirestassured.spotifyapi.apiClient;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * https://developer.spotify.com/documentation/web-api/tutorials/client-credentials-flow
 * */

public class AuthorizationTokenGenerator {

    private static String accessToken;
    private static Date expiresAt;

    private static String refreshToken;
    private static String tokenType;
    private static String tokenScope;

    private static final String TOKEN_URL = "https://accounts.spotify.com/api/token"; // ?
    private static final String AUTH_URL = "https://accounts.spotify.com/authorize";

    private static final RequestSpecification ACCESS_TOKEN_SPEC_THROUGH_CLIENT_CREDENTIALS = ReqSpecification.getAuthRequestSpec_throughClientCredentials();
    private static final RequestSpecification ACCESS_TOKEN_SPEC_THROUGH_AUTHORIZATION = ReqSpecification.getAuthRequestSpec_throughAuthorization();

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationTokenGenerator.class);


    // CLIENT CREDENTIALS FLOW
    /**
     *  "Request authorization"
     *  https://developer.spotify.com/documentation/web-api/tutorials/client-credentials-flow
     * */
    public static String generateValidAccessToken_clientCredentialsFlow() {
        if (accessToken == null || new Date().after(expiresAt)){
            refreshAccessToken_clientCredentialsFlow();
        }
        return accessToken;
    }

    private static synchronized void refreshAccessToken_clientCredentialsFlow() {
        Response response = RestAssured
                .given()
                    .spec(ACCESS_TOKEN_SPEC_THROUGH_CLIENT_CREDENTIALS)
                    .formParam("grant_type", "client_credentials")
                .when()
                    .request(Method.POST)
                .then()
                    .log().ifError()

                    .assertThat().statusCode(200)
                    .extract().response();
        try {
            accessToken = response.jsonPath().getString("access_token");
            int expiresIn = response.jsonPath().getInt("expires_in"); // The time period (in seconds) for which the access token is valid.
            expiresAt = new Date(System.currentTimeMillis() + (expiresIn - 10) * 1000L);

            logger.info("LOGGER - AuthorizationTokenGenerator - accessToken: " + accessToken + ", expiry date: " + expiresAt);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to get access token: " + response.statusCode());
        }
    }

    // AUTHORIZATION FLOW
    /**
     * "Request User Authorization"
     *  https://developer.spotify.com/documentation/web-api/tutorials/code-flow
     * */
    public static String getRequestAuthorizationCode() {
        String url = AUTH_URL + "?" +
                "response_type=code" +
                "&client_id=" + SpotifyClientService.getClientId() +
                "&scope=user-read-private%20user-read-email" +
                "&redirect_uri=" + SpotifyClientService.getRedirectUri() +
                "&state=RANDOM_STRING";

        System.out.println("Open in browser and login to url: " + url);
        return url;
    }

    public static String generateValidAccessToken_authorizationFlow(String code) {
        if (accessToken == null || new Date().after(expiresAt)){
            refreshValidAccessToken_authorizationFlow(code);
        }
        return accessToken;
    }

    /**
     * "Request an access token"
     *  https://developer.spotify.com/documentation/web-api/tutorials/code-flow
     * */
    private static String refreshValidAccessToken_authorizationFlow(String code) {
        Response response = RestAssured
                .given()
                    .spec(ACCESS_TOKEN_SPEC_THROUGH_AUTHORIZATION) // Authorization = Basic {client_id * client_secret} + Content-Type = application/x-www-form-urlencoded.
                    .formParam("grant_type", "authorization_code")
                    .formParam("code", code)
                    .formParam("redirect_uri", SpotifyClientService.getRedirectUri())
                .when()
                    .post()//TOKEN_URL
                .then()
                    .log().ifError()

                    .assertThat().statusCode(200)
                    .extract().response();
        try {
            accessToken = response.jsonPath().getString("access_token");

            tokenScope = response.jsonPath().getString("scope");
            tokenType = response.jsonPath().getString("token_type");
            refreshToken = response.jsonPath().getString("refresh_token");

            int expiresIn = response.jsonPath().getInt("expires_in"); // The time period (in seconds) for which the access token is valid.
            expiresAt = new Date(System.currentTimeMillis() + (expiresIn - 10) * 1000L);

            return accessToken;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to get access token: " + response.statusCode());
        }
    }
}
