package com.urjc.alumno.alvaro.sbc.service.impl;

import com.urjc.alumno.alvaro.sbc.api.common.utils.JenaUtils;
import com.urjc.alumno.alvaro.sbc.service.GraphService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GraphServiceImpl implements GraphService {

    @Override
    public Object getNode(final String endpoint, final String iri) {

        return JenaUtils.getNodeFromEndpoint(endpoint, iri);

    }

}
