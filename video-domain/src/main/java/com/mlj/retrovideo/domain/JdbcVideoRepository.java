package com.mlj.retrovideo.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcVideoRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcVideoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addVideo(VideoAddedEvent event) {
        jdbcTemplate.update("insert into videos (videoId, title) values (?, ?)",
                event.getVideoId(), event.getTitle());
    }

    public VideoView byId(String videoId) {
        return jdbcTemplate.queryForObject("select videoId, title from videos where videoId=?", new RowMapper<VideoView>() {
            @Override
            public VideoView mapRow(ResultSet resultSet, int i) throws SQLException {
                return new VideoView(resultSet.getString("videoId"), resultSet.getString("title"));
            }
        }, videoId);
    }

    public List<VideoView> all() {
        return jdbcTemplate.query("select * from videos", new RowMapper<VideoView>() {
            @Override
            public VideoView mapRow(ResultSet resultSet, int i) throws SQLException {
                return new VideoView(resultSet.getString("videoId"), resultSet.getString("title"));
            }
        });
    }

}
