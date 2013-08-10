package com.mlj.retrovideo.domain.video;

import java.util.List;
import java.util.Map;

public class VideoList {

    private final int page;
    private final long totalPages;
    private final long totalVideos;
    private final List<VideoView> videos;
    private final String searchKey;
    private Map<String, Integer> facets;

    public VideoList(int page, String searchKey, long totalVideos, List<VideoView> videos) {
        this.page = page;
        this.totalVideos = totalVideos;
        this.totalPages = Math.round(Math.ceil((double) totalVideos / 10));
        this.videos = videos;
        this.searchKey = searchKey;
    }

    public VideoList(VideoList videoList, Map<String, Integer> facets) {
        this.page = videoList.getPage();
        this.totalVideos = videoList.getTotalVideos();
        this.totalPages = videoList.getTotalPages();
        this.videos = videoList.getVideos();
        this.searchKey = videoList.getSearchKey();
        this.facets = facets;
    }

    public int getPage() {
        return page;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public List<VideoView> getVideos() {
        return videos;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public long getTotalVideos() {
        return totalVideos;
    }

    public Map<String, Integer> getFacets() {
        return facets;
    }

}
