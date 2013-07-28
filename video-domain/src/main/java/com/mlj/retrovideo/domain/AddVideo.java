package com.mlj.retrovideo.domain;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class AddVideo {

    @TargetAggregateIdentifier
    private final String videoId;
    private final String title;

    public AddVideo(String videoId, String title) {
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
