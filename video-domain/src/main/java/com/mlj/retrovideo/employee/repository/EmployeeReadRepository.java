package com.mlj.retrovideo.employee.repository;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.io.IOException;

import com.mlj.retrovideo.employee.AuthenticationFailedException;
import com.mlj.retrovideo.employee.EmployeeView;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
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

    public EmployeeView getEmployee(String employeeId) {
        GetResponse response = client.prepareGet("retrovideo", "employees", employeeId).execute().actionGet();
        try {
            return objectMapper.readValue(response.sourceAsString(), EmployeeView.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public EmployeeView getEmployeeByUsername(String username) {
        SearchResponse searchResponse = client.prepareSearch("retrovideo").setTypes("employees")
                .setQuery(QueryBuilders.matchQuery("username", username)).execute().actionGet();
        try {
            return objectMapper.readValue(searchResponse.hits().getAt(0).sourceAsString(), EmployeeView.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
