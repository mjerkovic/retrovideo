package com.mlj.retrovideo.domain.video;

import static com.google.common.collect.FluentIterable.from;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.search.facet.FacetBuilders.termsFacet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.mlj.retrovideo.domain.repository.ElasticSearchRepository;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.facet.terms.TermsFacet;
import org.elasticsearch.search.facet.terms.TermsFacetBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ElasticVideoRepository extends ElasticSearchRepository<VideoDto> {

    @Autowired
    public ElasticVideoRepository(Client client, ObjectMapper objectMapper) {
        super(client, objectMapper, "videos", VideoDto.class);
    }

    public void addVideo(VideoAdded event) {
        try {
            addToIndex(event.getVideoId(), createContent(event.getVideoId(), event.getTitle(), event.getYear(),
                    event.getCountry(), event.getDuration(), 0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addStock(StockAdded event) {
        try {
            VideoDto video = byId(event.getVideoId());
            video.setQuantity(video.getQuantity() + event.getQuantity());
            addToIndex(event.getVideoId(), createContent(event.getVideoId(), video.getTitle(), video.getYear(),
                    video.getCountry(), video.getDuration(), video.getQuantity()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public VideoBreakdown totalsFor(String category) {
        String adjustedCategory = (category.equals("country")) ? category + ".original" : category;
        return new VideoBreakdown(facetsFor(adjustedCategory));
    }

    public VideoList videosForPage(int pageNo) {
        SearchResponse searchResponse = client.prepareSearch("retrovideo").setTypes("videos")
                .setQuery(QueryBuilders.matchAllQuery()).setSize(10).setFrom((pageNo - 1) * 10)
                .addSort("title.sorted", SortOrder.ASC).execute().actionGet();

        return new VideoList(pageNo, searchResponse.hits().totalHits(), videosFromResponse(searchResponse));
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

    private XContentBuilder createContent(String videoId, String title, Integer year, String country, Integer duration,
                                          int quantity) throws IOException {
        return jsonBuilder().startObject().field("videoId", videoId)
                .field("title", title).field("year", year).field("country", country)
                .field("duration", duration).field("quantity", quantity).endObject();
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
