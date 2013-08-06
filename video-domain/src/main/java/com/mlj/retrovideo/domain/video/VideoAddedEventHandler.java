package com.mlj.retrovideo.domain.video;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class VideoAddedEventHandler {

    @EventHandler
    public void handle(VideoAdded event, JdbcVideoRepository videoRepository, ElasticVideoRepository elasticVideoRepository) {
        videoRepository.addVideo(event);
        elasticVideoRepository.addVideo(event);
    }

}
