package com.mlj.retrovideo.domain;

public class VideoView {

    private final String videoId;
    private final String title;


    public VideoView(String videoId, String title) {
        this.videoId = videoId;
        this.title = title;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getTitle() {
        return title;
    }

}
