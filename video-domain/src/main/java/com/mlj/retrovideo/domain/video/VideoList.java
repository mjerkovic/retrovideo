package com.mlj.retrovideo.domain.video;

import java.util.List;

public class VideoList {

    private final int page;
    private final int totalPages;
    private final List<VideoView> videos;

    public VideoList(long totalVideos, List<VideoView> videos) {
        this(0, totalVideos, videos);
    }

    public VideoList(int page, long totalVideos, List<VideoView> videos) {
        this.page = page;
        this.totalPages = Double.valueOf(Math.ceil(totalVideos / 10.0)).intValue();
        this.videos = videos;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<VideoView> getVideos() {
        return videos;
    }

}
