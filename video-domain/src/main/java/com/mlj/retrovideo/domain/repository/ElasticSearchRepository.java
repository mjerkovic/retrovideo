package com.mlj.retrovideo.domain.repository;

import static java.lang.String.format;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;

public abstract class ElasticSearchRepository<D> {

    protected final Client client;
    protected final ObjectMapper objectMapper;
    private final String indexType;
    private final Class<D> dtoClass;

    public ElasticSearchRepository(Client client, ObjectMapper objectMapper, String indexType, Class<D> dtoClass) {
        this.client = client;
        this.objectMapper = objectMapper;
        this.indexType = indexType;
        this.dtoClass = dtoClass;
    }

    protected void addToIndex(String id, XContentBuilder builder) throws IOException {
        client.prepareIndex("retrovideo", indexType, id).setSource(builder).setRefresh(true).execute().actionGet();
    }

    protected D byId(String videoId) {
        GetResponse getResponse = client.prepareGet("retrovideo", "videos", videoId).execute().actionGet();
        if (getResponse.exists()) {
            try {
                return objectMapper.readValue(getResponse.sourceAsString(), dtoClass);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        } else {
            throw new IllegalStateException(format("No video found with ID [%s]", videoId));
        }
    }


}
