package com.aws.class3.snsexercise.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws.sns")
public class SnsExerciseProperties {

    private String geocodingResponseTopicArn;

    public String getGeocodingResponseTopicArn() {
        return geocodingResponseTopicArn;
    }

    public void setGeocodingResponseTopicArn(String geocodingResponseTopicArn) {
        this.geocodingResponseTopicArn = geocodingResponseTopicArn;
    }
}
