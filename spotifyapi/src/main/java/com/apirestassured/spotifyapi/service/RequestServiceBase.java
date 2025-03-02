package com.apirestassured.spotifyapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface RequestServiceBase {

    default String mapObjectToJsonRequest(Object request) {
        ObjectMapper obj = new ObjectMapper();

        try {
            return obj.writeValueAsString(request);
        } catch (JsonProcessingException exception) {
            exception.printStackTrace();

            return "{\"error\": \"Could not map request object to JSON\"}";
        }
    }
}
