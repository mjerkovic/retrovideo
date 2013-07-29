package com.mlj.retrovideo.domain;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class AddVideoEventHandler {

    @EventHandler
    public void handle(VideoAddedEvent event, JdbcVideoRepository videoRepository) {
        videoRepository.addVideo(event);
    }

}
