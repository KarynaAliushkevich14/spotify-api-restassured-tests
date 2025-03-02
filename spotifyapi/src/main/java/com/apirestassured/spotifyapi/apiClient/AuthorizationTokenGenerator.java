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
    private static final RequestSpecification authRequestSpec = ReqSpecification.getAuthRequestSpec();

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationTokenGenerator.class);


    public static String generateValidAccessToken() {
        if (accessToken == null || new Date().after(expiresAt)){
            refreshAccessToken();
        }
        return accessToken;
    }

    private static synchronized void refreshAccessToken() {
        Response response = RestAssured
                .given()
                    .spec(authRequestSpec)
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
}
