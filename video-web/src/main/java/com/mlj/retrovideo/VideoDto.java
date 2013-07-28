package com.mlj.retrovideo;

public class VideoDto {

    private String videoId;
    private String title;

    public VideoDto() {
    }

    public VideoDto(String videoId, String title) {
        this.videoId = videoId;
        this.title = title;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "VideoDto{" +
                "videoId='" + videoId + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
