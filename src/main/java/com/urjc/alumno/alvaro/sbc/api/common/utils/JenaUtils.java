package com.urjc.alumno.alvaro.sbc.api.common.utils;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

public final class JenaUtils {

    private JenaUtils() { }

    public static ResultSet doQuery(final String endpoint, final String iri, final String query) {

        final Model model = ModelFactory.createDefaultModel();
        final Query jenaQuery = QueryFactory.create(query);
        final QueryExecution queryExecution = QueryExecutionFactory.sparqlService(endpoint, jenaQuery);
        final ResultSet resultSet = queryExecution.execSelect();
        return resultSet;

    }

}
