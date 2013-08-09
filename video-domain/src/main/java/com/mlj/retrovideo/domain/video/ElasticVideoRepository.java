package com.mlj.retrovideo.domain.video;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.IndicesExistsRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.io.stream.OutputStreamStreamOutput;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.facet.Facet;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.terms.TermsFacetBuilder;
import org.elasticsearch.search.sort.SortOrder;
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
        prepareIndex("retrovideo");
    }

    public void addVideo(VideoAdded event) {
        try {
            XContentBuilder builder = jsonBuilder().startObject().field("videoId", event.getVideoId())
                    .field("title", event.getTitle()).field("year", event.getYear()).field("country", event.getCountry())
                    .field("duration", event.getDuration()).endObject();
            client.prepareIndex("retrovideo", "videos", event.getVideoId()).setSource(builder).setRefresh(true).execute().actionGet();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public VideoList videosForPage(int pageNo) {
        TermsFacetBuilder facetBuilder = FacetBuilders.termsFacet("f").field("country");
        SearchResponse searchResponse = client.prepareSearch("retrovideo").setTypes("videos")
                .setQuery(QueryBuilders.matchAllQuery()).setFrom(pageNo).addSort("title.sorted", SortOrder.ASC)
                .addFacet(facetBuilder).execute().actionGet();
        List<VideoView> videos = Lists.newArrayList();
        for (SearchHit hit : searchResponse.hits().getHits()) {
            try {
                videos.add(objectMapper.readValue(hit.sourceAsString(), VideoView.class));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Facet facet = searchResponse.facets().facetsAsMap().get("f");
        return new VideoList(searchResponse.hits().totalHits(), videos);
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
                .mapping("videos", buildJsonMappings())).actionGet();
        try {
            response.writeTo(new OutputStreamStreamOutput(System.out));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private XContentBuilder buildJsonMappings() {
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
/*
                                .startObject("year").field("type", "integer").endObject()
                                .startObject("country").field("type", "string").endObject()
                                .startObject("duration").field("type", "integer").field("index", "not_analyzed").endObject()
*/
                            .endObject()
                        .endObject()
                    .endObject();
        } catch (IOException e) {
            throw new IllegalStateException("Could not build index mappings", e);
        }
    }

    private void deleteIndex(String index) {
        System.out.println("Deleting index");
        DeleteIndexResponse response = client.admin().indices().delete(new DeleteIndexRequest(index)).actionGet();
        if (!response.acknowledged()) {
            throw new IllegalStateException("Did not receive acknowledgement of index deletion.");
        }
    }

}
