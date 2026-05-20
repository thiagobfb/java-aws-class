package com.aws.class3.geocoding.service;

import com.aws.class3.geocoding.client.NominatimClient;
import com.aws.class3.geocoding.dto.GeocodingResponse;
import com.aws.class3.geocoding.dto.NominatimSearchResult;
import com.aws.class3.geocoding.exception.AddressNotFoundException;
import com.aws.class3.geocoding.exception.ExternalApiException;
import com.aws.class3.requestlog.domain.RequestLog;
import com.aws.class3.requestlog.repository.RequestLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class GeocodingService {

    private static final String ENDPOINT = "/api/geocoding/search";
    private static final String METHOD = "GET";

    private final NominatimClient nominatimClient;
    private final RequestLogRepository requestLogRepository;

    public GeocodingService(NominatimClient nominatimClient, RequestLogRepository requestLogRepository) {
        this.nominatimClient = nominatimClient;
        this.requestLogRepository = requestLogRepository;
    }

    public GeocodingResponse search(String street, String city, String state) {
        LocalDateTime startTime = LocalDateTime.now();
        int statusCode = 200;

        try {
            List<NominatimSearchResult> results = nominatimClient.search(street, city, state);

            if (results.isEmpty()) {
                throw new AddressNotFoundException("Nenhum endereco foi encontrado para os parametros informados.");
            }

            NominatimSearchResult firstResult = results.getFirst();
            return new GeocodingResponse(firstResult.lat(), firstResult.lon());
        } catch (AddressNotFoundException e) {
            statusCode = 404;
            throw e;
        } catch (ExternalApiException e) {
            statusCode = 502;
            throw e;
        } catch (RuntimeException e) {
            statusCode = 500;
            throw e;
        } finally {
            LocalDateTime endTime = LocalDateTime.now();
            long durationMs = ChronoUnit.MILLIS.between(startTime, endTime);
            requestLogRepository.save(new RequestLog(ENDPOINT, METHOD, startTime, endTime, durationMs, statusCode));
        }
    }
}