package com.urjc.alumno.alvaro.sbc.api.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.jena.query.*;

@Slf4j
public final class JenaUtils {

    private JenaUtils() { }

    public static ResultSet doQuery(final String endpoint, final String query) {

        final Query jenaQuery = QueryFactory.create(query);
        final QueryExecution queryExecution = QueryExecutionFactory.sparqlService(endpoint, jenaQuery);
        log.info("Calling {} with query {}", endpoint, query);
        return queryExecution.execSelect();

    }

}
