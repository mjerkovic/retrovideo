package com.mlj.retrovideo.video.event;

public class StockAdded {

    private final String videoId;
    private final int quantity;

    public StockAdded(String videoId, int quantity) {
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
