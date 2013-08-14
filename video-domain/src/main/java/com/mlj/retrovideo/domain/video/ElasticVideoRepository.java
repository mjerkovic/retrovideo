package com.mlj.retrovideo.domain.video;

import static com.google.common.collect.FluentIterable.from;
import static java.lang.String.format;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.search.facet.FacetBuilders.termsFacet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.IndicesExistsRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.io.stream.OutputStreamStreamOutput;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.facet.terms.TermsFacet;
import org.elasticsearch.search.facet.terms.TermsFacetBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ElasticVideoRepository {

    private final Client client;
    private final ObjectMapper objectMapper;
    private final SearchFileRepository searchFileRepository;

    @Autowired
    public ElasticVideoRepository(Client client, ObjectMapper objectMapper, SearchFileRepository searchFileRepository) {
        this.client = client;
        this.objectMapper = objectMapper;
        this.searchFileRepository = searchFileRepository;
        prepareIndex("retrovideo");
    }

    public void addVideo(VideoAdded event) {
        try {
            addVideoToIndex(event.getVideoId(), createContent(event.getVideoId(), event.getTitle(), event.getYear(),
                    event.getCountry(), event.getDuration(), 0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addStock(StockAdded event) {
        try {
            VideoDto video = byId(event.getVideoId());
            video.setQuantity(video.getQuantity() + event.getQuantity());
            addVideoToIndex(event.getVideoId(), createContent(event.getVideoId(), video.getTitle(), video.getYear(),
                    video.getCountry(), video.getDuration(), video.getQuantity()));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public VideoBreakdown totalsFor(String category) {
        String adjustedCategory = (category.equals("country")) ? category + ".original" : category;
        return new VideoBreakdown(facetsFor(adjustedCategory));
    }

    public VideoList all() {
        return videosForPage(1, searchFileRepository.createSearchFile(findAllVideoIds()));
    }

    public VideoDto byId(String videoId) {
        GetResponse getResponse = client.prepareGet("retrovideo", "videos", videoId).execute().actionGet();
        if (getResponse.exists()) {
            try {
                return objectMapper.readValue(getResponse.sourceAsString(), VideoDto.class);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        } else {
            throw new IllegalStateException(format("No video found with ID [%s]", videoId));
        }
    }

    public VideoList videosForPage(int pageNo, String searchFile) {
        List<String> ids = searchFileRepository.getIdsFrom(searchFile);
        SearchResponse searchResponse = searchByIdsForPage(ids, pageNo);
        return new VideoList(pageNo, searchFile, ids.size(), videosFromResponse(searchResponse));
    }

    private SearchResponse searchByIdsForPage(List<String> ids, int pageNo) {
        return client.prepareSearch("retrovideo").setTypes("videos")
                    .setQuery(QueryBuilders.idsQuery("videos").addIds(idsForPage(ids, pageNo)))
                    .addSort("title.sorted", SortOrder.ASC)
                    .execute().actionGet();
    }

    private List<VideoView> videosFromResponse(SearchResponse searchResponse) {
        return from(searchResponse.hits()).transform(new Function<SearchHit, VideoView>() {
            @Override
            public VideoView apply(SearchHit hit) {
                try {
                    return objectMapper.readValue(hit.sourceAsString(), VideoView.class);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).toImmutableList();
    }

    private String[] idsForPage(List<String> ids, int pageNo) {
        int min = (pageNo - 1) * 10;
        int max = Math.min(min + 10, ids.size());
        return ids.subList(min, max).toArray(new String[max - min]);
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
            e.printStackTrace();
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

    private void deleteIndex(String index) {
        System.out.println("Deleting index");
        DeleteIndexResponse response = client.admin().indices().delete(new DeleteIndexRequest(index)).actionGet();
        if (!response.acknowledged()) {
            throw new IllegalStateException("Did not receive acknowledgement of index deletion.");
        }
    }

    private void addVideoToIndex(String videoId, XContentBuilder builder) throws IOException {
        client.prepareIndex("retrovideo", "videos", videoId).setSource(builder).setRefresh(true).execute().actionGet();
    }

    private XContentBuilder createContent(String videoId, String title, Integer year, String country, Integer duration,
                                          int quantity) throws IOException {
        return jsonBuilder().startObject().field("videoId", videoId)
                .field("title", title).field("year", year).field("country", country)
                .field("duration", duration).field("quantity", quantity).endObject();
    }

    private List<String> findAllVideoIds() {
        SearchResponse searchResponse = client.prepareSearch("retrovideo").setTypes("videos")
                .setQuery(QueryBuilders.matchAllQuery()).setSize(numberOfVideosInIndex())
                .addSort("title.sorted", SortOrder.ASC).execute().actionGet();
        return FluentIterable.<SearchHit>from(searchResponse.hits()).transform(new Function<SearchHit, String>() {
            @Override
            public String apply(SearchHit searchHit) {
                return searchHit.id();
            }
        }).toImmutableList();
    }

    private Map<String, Integer> facetsFor(String category) {
        TermsFacetBuilder facetBuilder = termsFacet("results").field(category).size(numberOfVideosInIndex());
        SearchResponse searchResponse = client.prepareSearch("retrovideo").setTypes("videos")
                .setQuery(QueryBuilders.matchAllQuery()).addFacet(facetBuilder)
                .addSort(category, SortOrder.ASC).execute().actionGet();
        TermsFacet termsFacet = (TermsFacet) searchResponse.facets().facetsAsMap().get("results");
        Map<String, Integer> facets = Maps.newTreeMap();
        for (TermsFacet.Entry entry : termsFacet) {
            facets.put(entry.term(), entry.count());
        }
        return facets;
    }

    private int numberOfVideosInIndex() {
        return (int) client.prepareCount("retrovideo").setTypes("videos")
                .setQuery(QueryBuilders.matchAllQuery()).execute().actionGet().count();
    }
}
