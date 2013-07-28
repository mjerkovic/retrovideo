package com.mlj.retrovideo.domain;

public class VideoView {

    private final String videoId;
    private final String title;
    private final int year;
    private final int duration;


    public VideoView(String videoId, String title, int year, int duration) {
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

    public int getYear() {
        return year;
    }

    public int getDuration() {
        return duration;
    }

}
