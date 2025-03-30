package com.apirestassured.spotifyapi.apiClient;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope ("prototype")
public class RequestAuthorizationCode {

    private final String AUTH_URL = "https://accounts.spotify.com/authorize";

    /**
     * "Request User Authorization"
     *  https://developer.spotify.com/documentation/web-api/tutorials/code-flow
     *  generates url
     *  Display scopes
     * */
    public String getRequestAuthorizationCode() {
        String url = AUTH_URL + "?" +
                "response_type=code" +
                "&client_id=" + SpotifyClientService.getClientId() +
                "&scope=user-library-modify" +
                "&redirect_uri=" + SpotifyClientService.getRedirectUri() +
                "&state=RANDOM_STRING";

        System.out.println("Open in browser and login to url: " + url);
        return url;
    }
}
