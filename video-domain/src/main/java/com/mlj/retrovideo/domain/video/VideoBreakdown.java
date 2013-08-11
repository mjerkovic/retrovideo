package com.mlj.retrovideo.domain.video;

import java.util.Map;

public class VideoBreakdown {

    private final Map<String, Integer> facets;

    public VideoBreakdown(Map<String, Integer> facets) {
        this.facets = facets;
    }

    public Map<String, Integer> getFacets() {
        return facets;
    }

}
