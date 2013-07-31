package com.mlj.retrovideo.domain;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

public class Video extends AbstractAnnotatedAggregateRoot {

    @AggregateIdentifier
    private String videoId;
    private String title;
    private Integer year;
    private Integer duration;

    public Video() {
    }

    @CommandHandler
    public Video(AddVideo command) {
        apply(new VideoAdded(command.getVideoId(), command.getTitle(), command.getYear(), command.getDuration()));
    }

    @EventHandler
    public void on(VideoAdded event) {
        this.videoId = event.getVideoId();
        this.title = event.getTitle();
        this.year = event.getYear();
        this.duration = event.getDuration();
    }

}
