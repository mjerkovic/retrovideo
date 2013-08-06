package com.mlj.retrovideo.domain.employee;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeReadRepository {

    private final Client client;
    private final ObjectMapper objectMapper;

    @Autowired
    public EmployeeReadRepository(Client client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public EmployeeView authenticate(String username, String password) {
        SearchResponse searchResponse = client.prepareSearch("retrovideo").setTypes("employees")
                .setQuery(boolQuery().must(termQuery("username", username)).must(termQuery("password", password)))
                .execute().actionGet();
        return employeeView(searchResponse.hits());
    }

    public EmployeeView employeeView(SearchHits hits) {
        if (hits.totalHits() != 1) {
            throw new AuthenticationFailedException();
        }
        try {
            return objectMapper.readValue(hits.getAt(0).sourceAsString(), EmployeeView.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
