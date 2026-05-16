package com.aws.class3.geocoding.service;

import com.aws.class3.geocoding.client.NominatimClient;
import com.aws.class3.geocoding.dto.GeocodingResponse;
import com.aws.class3.geocoding.dto.NominatimSearchResult;
import com.aws.class3.geocoding.exception.AddressNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GeocodingServiceTest {

    @Mock
    private NominatimClient nominatimClient;

    @InjectMocks
    private GeocodingService geocodingService;

    @Test
    void shouldReturnFirstResultFromNominatim() {
        when(nominatimClient.search("Paulista", "Sao Paulo", "SP"))
                .thenReturn(List.of(
                        new NominatimSearchResult("-23.5613991", "-46.6558812"),
                        new NominatimSearchResult("-23.0000000", "-46.0000000")
                ));

        GeocodingResponse response = geocodingService.search("Paulista", "Sao Paulo", "SP");

        assertThat(response.lat()).isEqualTo("-23.5613991");
        assertThat(response.lon()).isEqualTo("-46.6558812");
    }

    @Test
    void shouldThrowNotFoundWhenNominatimReturnsNoResults() {
        when(nominatimClient.search("Paulista", "Sao Paulo", "SP"))
                .thenReturn(List.of());

        assertThatThrownBy(() -> geocodingService.search("Paulista", "Sao Paulo", "SP"))
                .isInstanceOf(AddressNotFoundException.class)
                .hasMessage("Nenhum endereco foi encontrado para os parametros informados.");
    }
}
