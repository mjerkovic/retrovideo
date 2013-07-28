package com.mlj.retrovideo.domain;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

public class Video extends AbstractAnnotatedAggregateRoot {

    @AggregateIdentifier
    private String videoId;
    private String title;

    public Video() {
    }

    @CommandHandler
    public Video(AddVideo command) {
        apply(new VideoAddedEvent(command.getVideoId(), command.getTitle()));
    }

    @EventHandler
    public void on(VideoAddedEvent event) {
        this.videoId = event.getVideoId();
        this.title = event.getTitle();
    }

}
