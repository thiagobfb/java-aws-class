package com.aws.class3.geocoding.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record NominatimSearchResult(String lat, String lon) {
}
