package com.apirestassured.spotifyapi.apiClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * https://developer.spotify.com/documentation/web-api/concepts/api-calls
 * */

@Component
@Scope("prototype")
public class ApiClient {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(ApiClient.class);


    public <T> T sendGenericRequest_ClientCredentialsFlow(String endpoint, Method httpMethod, @Nullable String jsonBody, Class<T> responseClass) {

        // we move accessToken here, so it will be requested before every new apiRequest
        String accessToken = AuthorizationTokenGenerator.generateValidAccessToken_clientCredentialsFlow();

        Response response = RestAssured
                .given()
                    .spec(getApiRequestSpecificationWithAccessToken(accessToken))
                    .body(jsonBody != null ? jsonBody : "")
                .when()
                    .request(httpMethod, endpoint)
                .then()
                    .log().all()
                    .log().ifError()
                    .log().ifValidationFails()

                .assertThat().statusCode(200)

                .extract().response();
        try {
            logger.info("LOGGER - ApiClient - endpoint: " + endpoint + ", jsonBody: " + jsonBody);

            return objectMapper.readValue(response.getBody().asString(), responseClass);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't deserialize JSON response body to an object from sendRequest()" + responseClass.getSimpleName(), e);
        }
    }

    private RequestSpecification getApiRequestSpecificationWithAccessToken(String accessToken) {
        return ReqSpecification.getApiRequestSpec(accessToken);
    }



    public <T> T sendGenericRequest_Authorization(String endpoint, Method httpMethod, @Nullable String jsonBody, Class<T> responseClass) {

        // we move accessToken here, so it will be requested before every new apiRequest
        String accessToken = AuthorizationTokenGenerator.generateValidAccessToken_clientCredentialsFlow();

        Response response = RestAssured
                .given()
                .spec(getApiRequestSpecificationWithAccessToken(accessToken))
                .body(jsonBody != null ? jsonBody : "")
                .when()
                .request(httpMethod, endpoint)
                .then()
                .log().all()
                .log().ifError()
                .log().ifValidationFails()

                .assertThat().statusCode(200)

                .extract().response();
        try {
            logger.info("LOGGER - ApiClient - endpoint: " + endpoint + ", jsonBody: " + jsonBody);

            return objectMapper.readValue(response.getBody().asString(), responseClass);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't deserialize JSON response body to an object from sendRequest()" + responseClass.getSimpleName(), e);
        }
    }
}
