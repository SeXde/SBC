package com.urjc.alumno.alvaro.sbc.api.common.utils;

import com.urjc.alumno.alvaro.sbc.service.exception.SBCException;
import lombok.extern.slf4j.Slf4j;
import org.apache.jena.query.*;
import org.springframework.http.HttpStatus;

@Slf4j
public final class JenaUtils {

    private JenaUtils() { }

    public static ResultSet doQuery(final String endpoint, final String query) {

        final Query jenaQuery = QueryFactory.create(query);
        try {

            final QueryExecution queryExecution = QueryExecutionFactory.sparqlService(endpoint, jenaQuery);
            log.info("Calling {} with query {}", endpoint, query);
            return queryExecution.execSelect();

        } catch (Exception e) {

            log.error("Error calling endpoint {}, got exception: '{}'", endpoint, e.getMessage());
            throw new SBCException(String.format("Error calling %s", endpoint), HttpStatus.SERVICE_UNAVAILABLE);

        }

    }

}
