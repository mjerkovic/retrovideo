package com.mlj.retrovideo.domain.repository;

import java.util.Map;

public class FacetResults {

    private final Map<String, Integer> facets;

    public FacetResults(Map<String, Integer> facets) {
        this.facets = facets;
    }

    public Map<String, Integer> getFacets() {
        return facets;
    }

}
