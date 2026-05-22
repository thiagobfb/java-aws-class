package com.aws.class3.geocoding.service;

import com.aws.class3.geocoding.client.NominatimClient;
import com.aws.class3.geocoding.dto.GeocodingResponse;
import com.aws.class3.geocoding.dto.NominatimSearchResult;
import com.aws.class3.geocoding.exception.AddressNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeocodingService {

    private final NominatimClient nominatimClient;

    public GeocodingService(NominatimClient nominatimClient) {
        this.nominatimClient = nominatimClient;
    }

    public GeocodingResponse search(String message) {
        List<NominatimSearchResult> results = nominatimClient.search(message);

        if (results.isEmpty()) {
            throw new AddressNotFoundException("Nenhum endereco foi encontrado para os parametros informados.");
        }

        NominatimSearchResult firstResult = results.getFirst();
        return new GeocodingResponse(firstResult.lat(), firstResult.lon());
    }
}
