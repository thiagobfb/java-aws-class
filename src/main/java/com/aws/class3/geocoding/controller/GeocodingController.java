package com.aws.class3.geocoding.controller;

import com.aws.class3.geocoding.dto.GeocodingResponse;
import com.aws.class3.geocoding.service.GeocodingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/geocoding")
@Tag(name = "Geocoding", description = "Consulta latitude e longitude a partir de endereco.")
public class GeocodingController {

    private final GeocodingService geocodingService;

    public GeocodingController(GeocodingService geocodingService) {
        this.geocodingService = geocodingService;
    }

    @GetMapping("/search")
    @Operation(
            summary = "Busca latitude e longitude",
            description = "Consulta o primeiro resultado retornado pela API Search do Nominatim.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Coordenadas encontradas",
                            content = @Content(schema = @Schema(implementation = GeocodingResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Parametros obrigatorios ausentes"),
                    @ApiResponse(responseCode = "404", description = "Endereco nao encontrado"),
                    @ApiResponse(responseCode = "502", description = "Erro ao consultar a API externa")
            }
    )
    public GeocodingResponse search(
            @Parameter(description = "Rua ou avenida", example = "Avenida Paulista")
            @RequestParam String street,
            @Parameter(description = "Cidade", example = "Sao Paulo")
            @RequestParam String city,
            @Parameter(description = "Estado", example = "SP")
            @RequestParam String state) {
        return geocodingService.search(street, city, state);
    }
}
