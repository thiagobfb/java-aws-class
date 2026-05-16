package com.aws.class3.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI class3OpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Class3 Geocoding API")
                        .description("API REST para consulta de latitude e longitude via Nominatim.")
                        .version("v1")
                        .contact(new Contact().name("Class3")));
    }
}
