package com.mlj.retrovideo.domain.video;

import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;

public class ElasticVideoReadRepository implements ReadRepository {

    private final Client client;
    private final ObjectMapper objectMapper;

    @Autowired
    public ElasticVideoReadRepository(Client client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    @Override
    public VideoDto byId(String videoId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
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

    @Override
    public boolean userExists(String userName, String password) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
