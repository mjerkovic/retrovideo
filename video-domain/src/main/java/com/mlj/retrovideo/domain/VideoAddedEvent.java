package com.mlj.retrovideo.domain;

public class VideoAddedEvent {

    private final String videoId;
    private final String title;

    public VideoAddedEvent(String videoId, String title) {
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
