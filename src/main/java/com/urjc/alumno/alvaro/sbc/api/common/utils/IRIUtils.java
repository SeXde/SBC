package com.urjc.alumno.alvaro.sbc.api.common.utils;

import java.net.URL;

public final class IRIUtils {

    private IRIUtils() { }

    public static boolean isValidIri(final String iri) {

        try {

            new URL(iri).toURI();
            return true;

        } catch (Exception e) {

            return false;

        }

    }

}
