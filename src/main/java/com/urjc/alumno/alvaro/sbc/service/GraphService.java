package com.urjc.alumno.alvaro.sbc.service;

import com.urjc.alumno.alvaro.sbc.api.common.dto.NodeSearchResponseDTO;

public interface GraphService {

    NodeSearchResponseDTO getNode(String endpoint, String iri);

}
