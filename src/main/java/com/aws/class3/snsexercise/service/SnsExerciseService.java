package com.aws.class3.snsexercise.service;

import com.aws.class3.geocoding.dto.GeocodingResponse;
import com.aws.class3.geocoding.service.GeocodingService;
import com.aws.class3.snsexercise.config.SnsExerciseProperties;
import com.aws.class3.snsexercise.dto.ReceivedMessage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

@Service
public class SnsExerciseService {

    private final GeocodingService geocodingService;
    private final SnsClient snsClient;
    private final SnsExerciseProperties properties;
    private final ObjectMapper objectMapper;

    public SnsExerciseService(
            GeocodingService geocodingService,
            SnsClient snsClient,
            SnsExerciseProperties properties,
            ObjectMapper objectMapper) {
        this.geocodingService = geocodingService;
        this.snsClient = snsClient;
        this.properties = properties;
        this.objectMapper = objectMapper;
    }

    public String processMessage(String rawBody) {
        ReceivedMessage receivedMessage = extractMessage(rawBody);
        GeocodingResponse geocodingResponse = geocodingService.search(receivedMessage.message());
        String publishedMessage = geocodingResponse.lat() + "," + geocodingResponse.lon();

        snsClient.publish(PublishRequest.builder()
                .topicArn(properties.getGeocodingResponseTopicArn())
                .message(publishedMessage)
                .build());

        return publishedMessage;
    }

    private ReceivedMessage extractMessage(String rawBody) {
        String body = rawBody == null ? "" : rawBody.trim();

        try {
            JsonNode jsonNode = objectMapper.readTree(body);

            if (jsonNode != null && jsonNode.isObject() && jsonNode.hasNonNull("message")) {
                String id = jsonNode.hasNonNull("id") ? jsonNode.get("id").asText() : null;
                String message = jsonNode.get("message").asText();
                return new ReceivedMessage(id, message);
            }
        } catch (Exception ignored) {
            // Fallback para texto puro no corpo da requisicao.
        }

        return new ReceivedMessage(null, body);
    }
}
