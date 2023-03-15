package com.urjc.alumno.alvaro.sbc.api.controller;

import com.urjc.alumno.alvaro.sbc.api.common.dto.NodeSearchRequestDTO;
import com.urjc.alumno.alvaro.sbc.service.GraphService;
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

    @PostMapping("/searches")
    public ResponseEntity<Object> getNode(@RequestBody final NodeSearchRequestDTO nodeSearch) {

        log.info("Received request to search {} from endpoint {}", nodeSearch.getIri(), nodeSearch.getEndpoint());
        return ResponseEntity.ok(graphService.getNode(nodeSearch.getEndpoint(), nodeSearch.getIri()));

    }

}
