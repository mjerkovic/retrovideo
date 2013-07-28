package com.mlj.retrovideo;

public class VideoDto {

    private String videoId;
    private String title;
    private Integer year;
    private Integer duration;

    public VideoDto() {
    }

    public VideoDto(String videoId, String title, Integer year, Integer duration) {
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

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "VideoDto{" +
                "videoId='" + videoId + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
