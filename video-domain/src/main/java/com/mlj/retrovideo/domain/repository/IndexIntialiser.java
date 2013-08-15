package com.mlj.retrovideo.domain.repository;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.IndicesExistsRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.io.stream.OutputStreamStreamOutput;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.springframework.beans.factory.InitializingBean;

public class IndexIntialiser implements InitializingBean {

    private final Client client;

    public IndexIntialiser(Client client) {
        this.client = client;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        prepareIndex("retrovideo");
    }

    private void prepareIndex(String index) {
        if (indexExists(index)) {
            deleteIndex(index);
        }
        createIndex(index);
    }

    private boolean indexExists(String index) {
        return client.admin().indices().exists(new IndicesExistsRequest(index)).actionGet().isExists();
    }

    private void createIndex(String index) {
        System.out.println("Creating index");
        CreateIndexResponse response = client.admin().indices().create(new CreateIndexRequest(index)
                .mapping("videos", buildVideoMappings())).actionGet();
        try {
            response.writeTo(new OutputStreamStreamOutput(System.out));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteIndex(String index) {
        System.out.println("Deleting index");
        DeleteIndexResponse response = client.admin().indices().delete(new DeleteIndexRequest(index)).actionGet();
        if (!response.acknowledged()) {
            throw new IllegalStateException("Did not receive acknowledgement of index deletion.");
        }
    }

    private XContentBuilder buildVideoMappings() {
        try {
            return jsonBuilder()
                    .startObject()
                    .startObject("videos")
                    .startObject("properties")
                    .startObject("title").field("type", "multi_field")
                    .startObject("fields")
                    .startObject("title").field("type", "string").field("index", "analyzed").endObject()
                    .startObject("sorted").field("type", "string").field("index", "not_analyzed").endObject()
                    .endObject()
                    .endObject()
                    .startObject("country").field("type", "multi_field")
                    .startObject("fields")
                    .startObject("country").field("type", "string").field("index", "analyzed").endObject()
                    .startObject("original").field("type", "string").field("index", "not_analyzed").endObject()
                    .endObject()
                    .endObject()
                    .endObject()
                    .endObject()
                    .endObject();
        } catch (IOException e) {
            throw new IllegalStateException("Could not build index mappings", e);
        }
    }

}
