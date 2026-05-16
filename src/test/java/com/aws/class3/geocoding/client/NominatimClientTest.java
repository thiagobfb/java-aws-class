package com.aws.class3.geocoding.client;

import com.aws.class3.geocoding.dto.NominatimSearchResult;
import com.aws.class3.geocoding.exception.ExternalApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class NominatimClientTest {

    private MockRestServiceServer server;
    private NominatimClient nominatimClient;

    @BeforeEach
    void setUp() {
        RestClient.Builder builder = RestClient.builder()
                .baseUrl("https://nominatim.openstreetmap.org")
                .defaultHeader("User-Agent", "class3-geocoding-app/1.0 (thiagobf.barbosa@gmail.com)");

        server = MockRestServiceServer.bindTo(builder).build();
        nominatimClient = new NominatimClient(builder.build(), "thiagobf.barbosa@gmail.com");
    }

    @Test
    void shouldCallNominatimWithExpectedParametersAndUserAgent() {
        server.expect(requestTo("https://nominatim.openstreetmap.org/search?street=Paulista&city=Sao%20Paulo&state=SP&email=thiagobf.barbosa@gmail.com&format=json&addressdetails=1&limit=1"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("User-Agent", "class3-geocoding-app/1.0 (thiagobf.barbosa@gmail.com)"))
                .andRespond(withSuccess("""
                        [{"lat":"-23.5613991","lon":"-46.6558812","display_name":"Avenida Paulista"}]
                        """, MediaType.APPLICATION_JSON));

        List<NominatimSearchResult> response = nominatimClient.search("Paulista", "Sao Paulo", "SP");

        assertThat(response).hasSize(1);
        assertThat(response.getFirst().lat()).isEqualTo("-23.5613991");
        assertThat(response.getFirst().lon()).isEqualTo("-46.6558812");
    }

    @Test
    void shouldThrowExternalApiExceptionWhenNominatimFails() {
        server.expect(requestTo("https://nominatim.openstreetmap.org/search?street=Paulista&city=Sao%20Paulo&state=SP&email=thiagobf.barbosa@gmail.com&format=json&addressdetails=1&limit=1"))
                .andRespond(withServerError());

        assertThatThrownBy(() -> nominatimClient.search("Paulista", "Sao Paulo", "SP"))
                .isInstanceOf(ExternalApiException.class)
                .hasMessage("Erro ao consultar a API do Nominatim.");
    }
}
