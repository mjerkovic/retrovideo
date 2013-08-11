package com.mlj.retrovideo.domain.video;

import java.util.List;

public class VideoList {

    private final int page;
    private final long totalPages;
    private final long totalVideos;
    private final List<VideoView> videos;
    private final String searchKey;

    public VideoList(int page, String searchKey, long totalVideos, List<VideoView> videos) {
        this.page = page;
        this.totalVideos = totalVideos;
        this.totalPages = Math.round(Math.ceil((double) totalVideos / 10));
        this.videos = videos;
        this.searchKey = searchKey;
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

}
