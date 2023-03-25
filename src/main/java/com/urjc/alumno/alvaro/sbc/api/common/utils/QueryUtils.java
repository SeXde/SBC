package com.urjc.alumno.alvaro.sbc.api.common.utils;

import com.urjc.alumno.alvaro.sbc.api.common.enums.FilterEnum;

import java.util.List;
import java.util.stream.Collectors;

public final class QueryUtils {

    private QueryUtils() { }

    public static String buildSelect(final String iri, final String queryLimit, final List<FilterEnum> filters) {

        final String initQuery =
                String.format(
                        """
                        SELECT distinct ?property ?hasValue ?isValueOf
                        WHERE {
                          { <%s> ?property ?hasValue }
                          UNION
                          { ?isValueOf ?property <%s> }
                        """, iri, iri
                );

        final String midQuery =
                filters
                        .stream()
                        .map(filter -> "FILTER(?property != <" + FilterEnum.getIri(filter) + ">)")
                        .collect(Collectors.joining("\n"));

        final String endQuery =
                String.format(
                        """
                        
                        } limit %s
                        """, queryLimit
                );

        return initQuery + midQuery + endQuery;

    }

    public static String buildIsProperty(final String iri) {

        return
                String.format(
                        """
                        SELECT distinct (count(?isValueOf) as ?count)
                        WHERE {
                            ?isValueOf <%s> ?hasValue
                        }
                        """, iri
                );

    }

    public static String buildExists(final String iri) {

        return
                String.format(
                        """
                        SELECT distinct (count(?isValueOf) as ?count)
                        WHERE {
                            { <%s> ?property ?hasValue }
                            UNION
                            { ?isValueOf ?property <%s> }
                            UNION
                            { ?isValueOf <%s> ?hasValue }
                        }
                        """, iri, iri, iri
                );

    }

}
