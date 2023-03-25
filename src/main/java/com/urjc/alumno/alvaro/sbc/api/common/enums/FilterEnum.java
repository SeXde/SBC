package com.urjc.alumno.alvaro.sbc.api.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public enum FilterEnum {

    TYPE("type", PrefixEnum.RDF),
    COMMENT("comment", PrefixEnum.RDFS),
    SEE_ALSO("seeAlso", PrefixEnum.RDFS),
    WIKI_PAGE_LINK("wikiPageWikiLink", PrefixEnum.DBO),
    WIKI_PAGE_EXTERNAL_LINK("wikiPageExternalLink", PrefixEnum.DBO),
    SAME_AS("sameAs", PrefixEnum.OWL),
    WIKI_PAGE_USES_TEMPLATE("wikiPageUsesTemplate", PrefixEnum.DBP),
    ABSTRACT("abstract", PrefixEnum.DBO),
    BLANK_NAME("blankname", PrefixEnum.DBP),
    WIKI_PAGE_REDIRECTS("wikiPageRedirects", PrefixEnum.DBO),
    WIKI_PAGE_DISAMBIGUATES("wikiPageDisambiguates", PrefixEnum.DBO),
    WIKI_PAGE_ID("wikiPageID", PrefixEnum.DBO),
    WIKI_PAGE_REVISION_ID("wikiPageRevisionID", PrefixEnum.DBO);


    private final String name;
    private final PrefixEnum prefix;

    public static String getIri(final FilterEnum filterEnum) {

        return filterEnum.getPrefix().getName() + filterEnum.getName();

    }

    public static List<FilterEnum> getAllFilters() {

        return Arrays.stream(FilterEnum.values()).toList();

    }

    public static String printAll() {

        return Arrays.toString(FilterEnum.values());

    }

}
