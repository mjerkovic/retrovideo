package com.mlj.retrovideo.domain.video;

public class VideoAdded {

    private final String videoId;
    private final String title;
    private final Integer year;
    private final Integer duration;

    public VideoAdded(String videoId, String title, Integer year, Integer duration) {
        this.videoId = videoId;
        this.title = title;
        this.year = year;
        this.duration = duration;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getTitle() {
        return title;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getDuration() {
        return duration;
    }

}
