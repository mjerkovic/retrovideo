package com.mlj.retrovideo.domain;

import org.axonframework.eventhandling.annotation.EventHandler;

public class AddVideoEventHandler {

    @EventHandler
    public void handle(VideoAddedEvent event) {
        System.out.println("Added: " + event.getVideoId() + " (" + event.getTitle() + ")");
    }

}
