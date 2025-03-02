package com.apirestassured.spotifyapi.model.requestBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // except null props/ fields from JSON
public class SaveAlbumsForCurrentUserRequestBuilder {

    private List<String> ids;

}
