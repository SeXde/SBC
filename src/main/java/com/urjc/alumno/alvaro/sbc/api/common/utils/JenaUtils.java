package com.urjc.alumno.alvaro.sbc.api.common.utils;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

public final class JenaUtils {

    private JenaUtils() { }

    public static Object getNodeFromEndpoint(final String endpoint, final String iri) {

        final String query = String.format(
                """
                SELECT ?property ?hasValue ?isValueOf
                WHERE {
                  { %s ?property ?hasValue }
                  UNION
                  { ?isValueOf ?property %s }
                }
                """, iri, iri
        );

        final Model model = ModelFactory.createDefaultModel();
        final Query jenaQuery = QueryFactory.create(query);
        final QueryExecution queryExecution = QueryExecutionFactory.sparqlService(endpoint, jenaQuery);
        final ResultSet resultSet = queryExecution.execSelect();
        while (resultSet.hasNext()) {

            final QuerySolution next = resultSet.next();
            System.out.println(next.getResource("property").getLocalName());

        }
        return ResultSetFormatter.asText(resultSet);

    }

}
