package com.mlj.retrovideo.video.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class AddStock {

    @TargetAggregateIdentifier
    private final String videoId;
    private final int quantity;

    public AddStock(String videoId, int quantity) {
        this.videoId = videoId;
        this.quantity = quantity;
    }

    public String getVideoId() {
        return videoId;
    }

    public int getQuantity() {
        return quantity;
    }

}
