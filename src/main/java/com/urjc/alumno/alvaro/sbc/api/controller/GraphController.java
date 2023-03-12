package com.urjc.alumno.alvaro.sbc.api.controller;

import com.urjc.alumno.alvaro.sbc.service.GraphService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.urjc.alumno.alvaro.sbc.api.common.constants.ApiConstants.ENDPOINT_PATH;
import static com.urjc.alumno.alvaro.sbc.api.common.constants.ApiConstants.IRI_PATH;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/graphs")
public class GraphController {

    private final GraphService graphService;

    @GetMapping("/{" + ENDPOINT_PATH + "}/nodes/{" + IRI_PATH +"}")
    public ResponseEntity<String> getNode(@PathVariable final String endpoint, @PathVariable final String iri) {
        return ResponseEntity.ok(String.format("Searching %s from %s", iri, endpoint));
    }

}
