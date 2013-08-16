package com.mlj.retrovideo.domain.video;

import java.util.List;

public class VideoList {

    private final int page;
    private final long totalPages;
    private final List<VideoView> videos;

    public VideoList(int page, long totalVideos, List<VideoView> videos) {
        this.page = page;
        this.totalPages = Math.round(Math.ceil((double) totalVideos / 10));
        this.videos = videos;
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

}
