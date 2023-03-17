package com.urjc.alumno.alvaro.sbc.api.common.utils;

public final class QueryUtils {

    public static String buildSelect(final String iri, final String queryLimit) {

        return
                String.format(
                        """
                        SELECT distinct ?property ?hasValue ?isValueOf
                        WHERE {
                          { <%s> ?property ?hasValue }
                          UNION
                          { ?isValueOf ?property <%s> }
                        } limit %s
                        """, iri, iri, queryLimit
                );

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
