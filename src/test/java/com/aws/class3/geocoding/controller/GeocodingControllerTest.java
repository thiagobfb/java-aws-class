package com.aws.class3.geocoding.controller;

import com.aws.class3.geocoding.dto.GeocodingResponse;
import com.aws.class3.geocoding.exception.AddressNotFoundException;
import com.aws.class3.geocoding.handler.GlobalExceptionHandler;
import com.aws.class3.geocoding.service.GeocodingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GeocodingController.class)
@Import(GlobalExceptionHandler.class)
class GeocodingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GeocodingService geocodingService;

    @Test
    void shouldReturnLatAndLon() throws Exception {
        when(geocodingService.search("Paulista", "Sao Paulo", "SP"))
                .thenReturn(new GeocodingResponse("-23.5613991", "-46.6558812"));

        mockMvc.perform(get("/api/geocoding/search")
                        .param("street", "Paulista")
                        .param("city", "Sao Paulo")
                        .param("state", "SP"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.lat").value("-23.5613991"))
                .andExpect(jsonPath("$.lon").value("-46.6558812"));
    }

    @Test
    void shouldReturnBadRequestWhenStreetIsMissing() throws Exception {
        mockMvc.perform(get("/api/geocoding/search")
                        .param("city", "Sao Paulo")
                        .param("state", "SP"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("O parâmetro 'street' é obrigatório."));
    }

    @Test
    void shouldReturnNotFoundWhenServiceDoesNotFindAddress() throws Exception {
        when(geocodingService.search("Paulista", "Sao Paulo", "SP"))
                .thenThrow(new AddressNotFoundException("Nenhum endereco foi encontrado para os parametros informados."));

        mockMvc.perform(get("/api/geocoding/search")
                        .param("street", "Paulista")
                        .param("city", "Sao Paulo")
                        .param("state", "SP"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Nenhum endereco foi encontrado para os parametros informados."));
    }
}
