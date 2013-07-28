package com.mlj.retrovideo.domain;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddVideoEventHandler {

    @Autowired
    private JdbcVideoRepository videoRepository;

    @EventHandler
    public void handle(VideoAddedEvent event) {
        System.out.println("event = " + event);
        videoRepository.addVideo(event);
    }

}
