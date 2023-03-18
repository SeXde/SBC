package com.urjc.alumno.alvaro.sbc.service.impl;

import com.urjc.alumno.alvaro.sbc.api.common.constants.QueryConstants;
import com.urjc.alumno.alvaro.sbc.api.common.dto.LinkDTO;
import com.urjc.alumno.alvaro.sbc.api.common.dto.NodeDTO;
import com.urjc.alumno.alvaro.sbc.api.common.dto.NodeSearchResponseDTO;
import com.urjc.alumno.alvaro.sbc.api.common.enums.FlowEnum;
import com.urjc.alumno.alvaro.sbc.api.common.utils.IRIUtils;
import com.urjc.alumno.alvaro.sbc.api.common.utils.JenaUtils;
import com.urjc.alumno.alvaro.sbc.api.common.utils.QueryUtils;
import com.urjc.alumno.alvaro.sbc.service.GraphService;
import com.urjc.alumno.alvaro.sbc.service.exception.SBCException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;

import static com.urjc.alumno.alvaro.sbc.api.common.constants.QueryConstants.COUNT;

@Service
@RequiredArgsConstructor
@Slf4j
public class GraphServiceImpl implements GraphService {

    @Override
    public NodeSearchResponseDTO getNode(final String endpoint, final String iri, final String size, final boolean verifyNode) {

        if (verifyNode) {

            checkIri(iri, endpoint);

        }

        final NodeSearchResponseDTO nodeSearchResponseDTO = new NodeSearchResponseDTO();
        nodeSearchResponseDTO.setOriginNode(NodeDTO.builder().iri(iri).build());
        nodeSearchResponseDTO.setLinks(new ArrayList<>());
        nodesAction(QueryUtils.buildSelect(iri, size), endpoint, (querySolution) ->
                buildLink(
                        querySolution.get(QueryConstants.PROPERTY),
                        querySolution.get(QueryConstants.HAS_VALUE),
                        querySolution.get(QueryConstants.IS_VALUE_OF),
                        nodeSearchResponseDTO
                )
        );

        log.info(
                "Node {} was successfully built with {} connections",
                nodeSearchResponseDTO.getOriginNode().getIri(),
                nodeSearchResponseDTO.getLinks().size()
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
                .parallelStream()
                .filter(link -> Objects.equals(edge, link.getEdgeName()))
                .findAny()
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
                        .parallelStream()
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

            destinationNode.setIri(StringUtils.isBlank(rdfNode.asResource().getURI())
                    ? null
                    : rdfNode.asResource().getURI());
            destinationNode.setName(StringUtils.isBlank(rdfNode.asResource().getLocalName())
                    ? null
                    : rdfNode.asResource().getLocalName());

        } else {

            destinationNode.setName(StringUtils.isBlank(rdfNode.asLiteral().getString())
                    ? null
                    : rdfNode.asLiteral().getString());

        }

    }

    private void checkIri(final String iri, final String endpoint) {

        if (!IRIUtils.isValidIri(iri)) {

            log.error("{} is not a valid IRI", iri);
            throw new SBCException(String.format("%s is not a valid IRI, please use full iri name, we don't use 'PREFIX'",
                    iri), HttpStatus.BAD_REQUEST
            );

        }

        nodesAction(QueryUtils.buildExists(iri), endpoint, (node) -> {
                    if (Objects.equals("0", node.get(COUNT).asLiteral().getString())) {

                        log.error("{} doesn't exist", iri);
                        throw new SBCException(String.format("%s doesn't exist for endpoint %s, please introduce a valid node",
                                iri, endpoint), HttpStatus.BAD_REQUEST
                        );

                    }
                }
        );

        nodesAction(QueryUtils.buildIsProperty(iri), endpoint, (node) -> {
                    if (!Objects.equals("0", node.get(COUNT).asLiteral().getString())) {

                        log.error("{} is an edge", iri);
                        throw new SBCException(String.format("%s is not a correct node, input must be only nodes and not edges",
                                iri), HttpStatus.BAD_REQUEST
                        );

                    }
                }
        );

    }

    private void nodesAction(final String query, final String endpoint, final Consumer<QuerySolution> action) {

        final ResultSet edgeSet = JenaUtils.doQuery(endpoint, query);
        log.info("Received response from {} with {} vars", endpoint, edgeSet.getResultVars());
        edgeSet.forEachRemaining(action);

    }

}
