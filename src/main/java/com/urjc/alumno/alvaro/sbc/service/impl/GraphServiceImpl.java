package com.urjc.alumno.alvaro.sbc.service.impl;

import com.urjc.alumno.alvaro.sbc.api.common.constants.QueryConstants;
import com.urjc.alumno.alvaro.sbc.api.common.dto.LinkDTO;
import com.urjc.alumno.alvaro.sbc.api.common.dto.NodeDTO;
import com.urjc.alumno.alvaro.sbc.api.common.dto.NodeSearchResponseDTO;
import com.urjc.alumno.alvaro.sbc.api.common.enums.FlowEnum;
import com.urjc.alumno.alvaro.sbc.api.common.utils.JenaUtils;
import com.urjc.alumno.alvaro.sbc.api.common.utils.QueryUtils;
import com.urjc.alumno.alvaro.sbc.service.GraphService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class GraphServiceImpl implements GraphService {

    @Override
    public NodeSearchResponseDTO getNode(final String endpoint, final String iri) {

        final NodeSearchResponseDTO nodeSearchResponseDTO = new NodeSearchResponseDTO();
        nodeSearchResponseDTO.setOriginNode(NodeDTO.builder().iri(iri).build());
        nodeSearchResponseDTO.setLinks(new ArrayList<>());
        final ResultSet nodeConnections = JenaUtils.doQuery(endpoint, iri, QueryUtils.buildSelect(iri));

        nodeConnections.forEachRemaining(querySolution ->

                buildLink(querySolution.get(QueryConstants.PROPERTY),
                        querySolution.get(QueryConstants.HAS_VALUE),
                        querySolution.get(QueryConstants.IS_VALUE_OF),
                        nodeSearchResponseDTO
                )

        );

        return nodeSearchResponseDTO;

    }

    private void buildLink(final RDFNode property,
                              final RDFNode value,
                              final RDFNode valueOf,
                              final NodeSearchResponseDTO nodeSearchResponseDTO
    ) {

        if (Objects.isNull(value) && Objects.isNull(valueOf)) {

            return;

        }

        final boolean isValueOf = Objects.isNull(value);
        final String edge = property.asResource().getLocalName();
        final LinkDTO linkDTO = nodeSearchResponseDTO
                .getLinks()
                .stream()
                .filter(link -> Objects.equals(edge, link.getEdgeName()))
                .findFirst()
                .orElseGet(() -> {

                    final LinkDTO newLinkDTO = new LinkDTO(
                            edge,
                            isValueOf ? FlowEnum.IN : FlowEnum.OUT,
                            new ArrayList<>()
                    );
                    nodeSearchResponseDTO.getLinks().add(newLinkDTO);
                    return newLinkDTO;

                });

        final NodeDTO destinationNode = new NodeDTO();
        buildDestinationNode(isValueOf ? valueOf : value, destinationNode);
        if (
                (!Objects.isNull(destinationNode.getIri())
                        || !StringUtils.isBlank(destinationNode.getName()))
                        && linkDTO
                        .getNodes()
                        .stream()
                        .noneMatch(node ->
                                Objects.equals(node.getIri(), destinationNode.getIri())
                                        && Objects.equals(node.getName(), destinationNode.getName())
                        )
        ) {
            linkDTO.getNodes().add(destinationNode);
        }

    }

    private void buildDestinationNode(final RDFNode rdfNode, final NodeDTO destinationNode) {

        if (rdfNode.isResource()) {

            destinationNode.setIri(StringUtils.isBlank(rdfNode.asResource().getURI()) ? null : rdfNode.asResource().getURI());
            destinationNode.setName(StringUtils.isBlank(rdfNode.asResource().getLocalName()) ? null : rdfNode.asResource().getLocalName());

        } else {

            destinationNode.setName(StringUtils.isBlank(rdfNode.asLiteral().getString()) ? null : rdfNode.asLiteral().getString());

        }

    }

}
