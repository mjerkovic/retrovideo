package com.mlj.retrovideo.domain;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class VideoAddedEventHandler {

    @EventHandler
    public void handle(VideoAdded event, JdbcVideoRepository videoRepository) {
        videoRepository.addVideo(event);
    }

}
