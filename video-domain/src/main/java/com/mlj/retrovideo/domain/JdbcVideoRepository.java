package com.mlj.retrovideo.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class JdbcVideoRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcVideoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addVideo(VideoAdded event) {
        jdbcTemplate.update("insert into videos (videoId, title, year, duration) values (?, ?, ?, ?)",
                event.getVideoId(), event.getTitle(), event.getYear(), event.getDuration());
    }

    public VideoView byId(String videoId) {
        return jdbcTemplate.queryForObject("select videoId, title, year, duration from videos where videoId=?",
                new RowMapper<VideoView>() {
            @Override
            public VideoView mapRow(ResultSet resultSet, int i) throws SQLException {
                VideoView result = new VideoView();
                result.setVideoId(resultSet.getString("videoId"));
                result.setTitle(resultSet.getString("title"));
                result.setYear(resultSet.getInt("year"));
                result.setDuration(resultSet.getInt("duration"));
                return result;
            }
        }, videoId);
    }

    public List<VideoView> all() {
        return jdbcTemplate.query("select * from videos", new RowMapper<VideoView>() {
            @Override
            public VideoView mapRow(ResultSet resultSet, int i) throws SQLException {
                VideoView result = new VideoView();
                result.setVideoId(resultSet.getString("videoId"));
                result.setTitle(resultSet.getString("title"));
                result.setYear(resultSet.getInt("year"));
                result.setDuration(resultSet.getInt("duration"));
                return result;
            }
        });
    }

    public boolean userExists(String userName, String password) {
        return jdbcTemplate.queryForInt("select count(firstname) from users where username = ? and password = ?",
                userName, password) == 1;
    }

}
