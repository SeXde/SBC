package com.urjc.alumno.alvaro.sbc.api.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NodeSearchRequestDTO {

    @Schema(description = "Endpoint where querying sparql", example = "http://dbpedia.org/sparql")
    private String endpoint;
    @Schema(description = "Desired node", example = "http://dbpedia.org/resource/José_Luis_Rodríguez_Zapatero")
    private String iri;

}
