package com.mlj.retrovideo.domain;

import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ElasticVideoRepository {

    private final Client client;
    private final ObjectMapper objectMapper;


    @Autowired
    public ElasticVideoRepository(Client client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public void addVideo(VideoAdded event) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("videoId", event.getVideoId())
                    .field("title", event.getTitle()).field("year", event.getYear()).field("duration", event.getDuration()).endObject();
            client.prepareIndex("retrovideo", "videos", event.getVideoId()).setSource(builder).setRefresh(true).execute().actionGet();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<VideoView> all() {
        SearchResponse searchResponse = client.prepareSearch("retrovideo").setTypes("videos")
                .setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
        List<VideoView> videos = Lists.newArrayList();
        for (SearchHit hit : searchResponse.hits().getHits()) {
            try {
                videos.add(objectMapper.readValue(hit.sourceAsString(), VideoView.class));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return videos;
    }


}
