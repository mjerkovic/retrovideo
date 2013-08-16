package com.mlj.retrovideo.repository;

import java.util.List;

public class ItemList<View> {

    private final int page;
    private final long totalPages;
    private final List<View> items;

    public ItemList(int page, long totalItems, List<View> items) {
        this.page = page;
        this.totalPages = Math.round(Math.ceil((double) totalItems / 10));
        this.items = items;
    }

    public int getPage() {
        return page;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public List<View> getItems() {
        return items;
    }

}
