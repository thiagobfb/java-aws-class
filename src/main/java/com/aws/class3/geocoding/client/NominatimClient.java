package com.aws.class3.geocoding.client;

import com.aws.class3.geocoding.dto.NominatimSearchResult;
import com.aws.class3.geocoding.exception.ExternalApiException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Component
public class NominatimClient {

    private static final ParameterizedTypeReference<List<NominatimSearchResult>> RESPONSE_TYPE =
            new ParameterizedTypeReference<>() {
            };

    private final RestClient restClient;
    private final String email;

    public NominatimClient(
            RestClient nominatimRestClient,
            @Value("${nominatim.email}") String email) {
        this.restClient = nominatimRestClient;
        this.email = email;
    }

    public List<NominatimSearchResult> search(String street, String city, String state) {
        URI uri = UriComponentsBuilder.fromPath("/search")
                .queryParam("street", street)
                .queryParam("city", city)
                .queryParam("state", state)
                .queryParam("email", email)
                .queryParam("format", "json")
                .queryParam("addressdetails", 1)
                .queryParam("limit", 1)
                .encode()
                .build()
                .toUri();

        try {
            List<NominatimSearchResult> response = restClient.get()
                    .uri(uri)
                    .retrieve()
                    .body(RESPONSE_TYPE);

            return response == null ? List.of() : response;
        } catch (RestClientException ex) {
            throw new ExternalApiException("Erro ao consultar a API do Nominatim.", ex);
        }
    }
}
