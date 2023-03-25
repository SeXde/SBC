package com.urjc.alumno.alvaro.sbc.api.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PrefixEnum {

    OWL("http://www.w3.org/2002/07/owl#"),
    RDF("http://www.w3.org/1999/02/22-rdf-syntax-ns#"),
    DBO("http://dbpedia.org/ontology/"),
    DBP("http://dbpedia.org/property/"),
    RDFS("http://www.w3.org/2000/01/rdf-schema#");

    private final String name;

}
