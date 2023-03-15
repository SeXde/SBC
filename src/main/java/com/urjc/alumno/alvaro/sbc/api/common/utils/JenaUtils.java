package com.urjc.alumno.alvaro.sbc.api.common.utils;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;

import java.util.Objects;

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
        resultSet.forEachRemaining(querySolution -> {
            try {
                final RDFNode rdfNode = querySolution.get("hasValue");
                if (Objects.isNull(rdfNode)) {
                    return;
                }
                if (rdfNode.isLiteral()) {
                    System.out.println(rdfNode.asLiteral().getString());
                } else if (rdfNode.isURIResource()) {
                    System.out.println(rdfNode.asResource().getURI());
                } else {
                    System.out.println("Que pollas es esto: " + rdfNode.toString());
                }
           } catch (Exception e) {
               System.out.println("wtf mama?");
           }
        });
        /*while (resultSet.hasNext()) {

            final QuerySolution next = resultSet.next();
            System.out.println(next.getResource("property").getLocalName());

        }*/
        return ResultSetFormatter.asText(resultSet);

    }

}
