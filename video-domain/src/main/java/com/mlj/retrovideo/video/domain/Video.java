package com.mlj.retrovideo.video.domain;

import java.util.concurrent.atomic.AtomicInteger;

import com.mlj.retrovideo.video.command.AddStock;
import com.mlj.retrovideo.video.command.AddVideo;
import com.mlj.retrovideo.video.event.StockAdded;
import com.mlj.retrovideo.video.event.VideoAdded;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

public class Video extends AbstractAnnotatedAggregateRoot {

    @AggregateIdentifier
    private String videoId;
    private String title;
    private Integer year;
    private String country;
    private Integer duration;
    private AtomicInteger quantity = new AtomicInteger(0);

    public Video() {
    }

    @CommandHandler
    public Video(AddVideo command) {
        apply(new VideoAdded(command.getVideoId(), command.getTitle(), command.getYear(), command.getCountry(),
                command.getDuration()));
    }

    @CommandHandler
    public void addStock(AddStock command) {
        apply(new StockAdded(command.getVideoId(), command.getQuantity()));
    }

    @EventHandler
    public void on(VideoAdded event) {
        this.videoId = event.getVideoId();
        this.title = event.getTitle();
        this.year = event.getYear();
        this.country = event.getCountry();
        this.duration = event.getDuration();
    }

    @EventHandler
    public void on (StockAdded event) {
        quantity.addAndGet(event.getQuantity());
    }

}
