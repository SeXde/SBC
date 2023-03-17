package com.urjc.alumno.alvaro.sbc.api.controller;

import com.urjc.alumno.alvaro.sbc.api.common.dto.NodeSearchRequestDTO;
import com.urjc.alumno.alvaro.sbc.api.common.dto.NodeSearchResponseDTO;
import com.urjc.alumno.alvaro.sbc.service.GraphService;
import com.urjc.alumno.alvaro.sbc.service.exception.dto.SBCErrorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/graphs")
public class GraphController {

    private final GraphService graphService;

    @Operation(summary = "Search node connections by iri")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Node connections were found successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = NodeSearchResponseDTO.class)
                    )
            }),
            @ApiResponse(responseCode = "400",
                    description = "Wrong iri, be sure to use a full iri and it cannot not be a property",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SBCErrorDTO.class)
                    )
            }),
            @ApiResponse(responseCode = "503",
                    description = "Unable to contact with desired endpoint",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SBCErrorDTO.class)
                    )
            })
    })
    @PostMapping("/searches")
    public ResponseEntity<NodeSearchResponseDTO> getNode(@RequestBody final NodeSearchRequestDTO nodeSearch) {

        log.info("Received request to search {} from endpoint {}", nodeSearch.getIri(), nodeSearch.getEndpoint());
        return ResponseEntity.ok(graphService.getNode(nodeSearch.getEndpoint(), nodeSearch.getIri()));

    }

}
