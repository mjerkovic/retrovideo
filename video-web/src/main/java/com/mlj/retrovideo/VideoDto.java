package com.mlj.retrovideo;

public class VideoDto {

    private String title;
    private Integer year;
    private Integer duration;

    public VideoDto() {
    }

    public VideoDto(String title, Integer year, Integer duration) {
        this.title = title;
        this.year = year;
        this.duration = duration;
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

    @Override
    public String toString() {
        return "VideoDto{" +
                "title='" + title + '\'' +
                ", year=" + year +
                ", duration=" + duration +
                '}';
    }

}
