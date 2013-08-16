package com.mlj.retrovideo.video;

public class VideoDto {

    private String videoId;
    private String title;
    private Integer year;
    private String country;
    private Integer duration;
    private Integer quantity;

    public VideoDto() {
    }

    public VideoDto(String title, Integer year, String country, Integer duration, Integer quantity) {
        this.title = title;
        this.year = year;
        this.country = country;
        this.duration = duration;
        this.quantity = quantity;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "VideoDto{" +
                "title='" + title + '\'' +
                ", year=" + year +
                ", country='" + country + '\'' +
                ", duration='" + duration + '\'' +
                ", quantity=" + quantity +
                '}';
    }

}
