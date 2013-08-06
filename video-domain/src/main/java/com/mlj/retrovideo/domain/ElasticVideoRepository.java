package com.mlj.retrovideo.domain;

import java.io.IOException;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ElasticVideoRepository {

    private final Client client;

    @Autowired
    public ElasticVideoRepository(Client client) {
        this.client = client;
    }

    public void addVideo(VideoAdded event) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("title", event.getTitle())
                    .field("year", event.getYear()).field("duration", event.getDuration()).endObject();
            client.prepareIndex("retrovideo", "videos", "1001").setSource(builder).setRefresh(true).execute().actionGet();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

}
