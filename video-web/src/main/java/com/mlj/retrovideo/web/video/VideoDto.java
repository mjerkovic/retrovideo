package com.mlj.retrovideo.web.video;

public class VideoDto {

    private String title;
    private Integer year;
    private String country;
    private Integer duration;

    public VideoDto() {
    }

    public VideoDto(String title, Integer year, String country, Integer duration) {
        this.title = title;
        this.year = year;
        this.country = country;
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

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return "VideoDto{" +
                "title='" + title + '\'' +
                ", year=" + year +
                ", country='" + country + '\'' +
                ", duration=" + duration +
                '}';
    }

}
