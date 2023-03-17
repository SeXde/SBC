package com.urjc.alumno.alvaro.sbc.api.common.utils;

public final class QueryUtils {

    public static String buildSelect(final String iri) {

        return
                String.format(
                        """
                        SELECT distinct ?property ?hasValue ?isValueOf
                        WHERE {
                          { <%s> ?property ?hasValue }
                          UNION
                          { ?isValueOf ?property <%s> }
                        }
                        """, iri, iri
                );

    }

    public String buildIsProperty(final String iri) {

        return
                String.format(
                        """
                        SELECT distinct count(?property) AS ?properties
                        WHERE {
                          { <%s> ?property ?hasValue }
                          UNION
                          { ?isValueOf ?property <%s> }
                        }
                        """, iri, iri
                );

    }

}
