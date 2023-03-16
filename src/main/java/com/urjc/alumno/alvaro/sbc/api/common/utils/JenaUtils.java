package com.urjc.alumno.alvaro.sbc.api.common.utils;

import com.urjc.alumno.alvaro.sbc.api.common.dto.LinkDTO;
import com.urjc.alumno.alvaro.sbc.api.common.dto.NodeSearchResponseDTO;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;

import java.util.Objects;

public final class JenaUtils {

    private JenaUtils() { }

    public static ResultSet getNodeConnections(final String endpoint, final String iri) {

        final String query = String.format(
                """
                SELECT ?property ?hasValue ?isValueOf
                WHERE {
                  { <%s> ?property ?hasValue }
                  UNION
                  { ?isValueOf ?property <%s> }
                }
                """, iri, iri
        );

        final Model model = ModelFactory.createDefaultModel();
        final Query jenaQuery = QueryFactory.create(query);
        final QueryExecution queryExecution = QueryExecutionFactory.sparqlService(endpoint, jenaQuery);
        final ResultSet resultSet = queryExecution.execSelect();
        return resultSet;

    }

}
