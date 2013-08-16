package com.mlj.retrovideo.domain.account;

import static com.google.common.collect.FluentIterable.from;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.List;

import com.google.common.base.Function;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ElasticAccountRepository {

    private final Client client;
    private final ObjectMapper objectMapper;

    @Autowired
    public ElasticAccountRepository(Client client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public void createAccount(AccountCreated event) {
        try {
            addAccountToIndex(event.getAccountNo(), createContent(event.getAccountNo(), event.getTitle(), event.getFirstName(),
                    event.getLastName(), event.getDateOfBirth(), event.getStreet(), event.getCity(), event.getPostcode()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AccountList accountsForPage(int pageNo) {
        SearchResponse searchResponse = client.prepareSearch("retrovideo").setTypes("accounts")
                .setQuery(QueryBuilders.matchAllQuery()).setSize(10).setFrom((pageNo - 1) * 10)
                .addSort("lastName", SortOrder.ASC).execute().actionGet();
        return new AccountList(pageNo, searchResponse.hits().totalHits(), accountsFromResponse(searchResponse));
    }

    private List<AccountView> accountsFromResponse(SearchResponse searchResponse) {
        return from(searchResponse.hits()).transform(new Function<SearchHit, AccountView>() {
            @Override
            public AccountView apply(SearchHit hit) {
                try {
                    return objectMapper.readValue(hit.sourceAsString(), AccountView.class);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).toImmutableList();
    }

    private void addAccountToIndex(String accountNo, XContentBuilder builder) {
        client.prepareIndex("retrovideo", "accounts", accountNo).setSource(builder).setRefresh(true).execute().actionGet();
    }

    private XContentBuilder createContent(String accountNo, String title, String firstName, String lastName,
                                          String dateOfBirth, String street, String city, String postcode) throws IOException {
        return jsonBuilder().startObject().field("accountNo", accountNo)
                .field("title", title).field("firstName", firstName).field("lastName", lastName)
                .field("dateOfBirth", dateOfBirth).field("street", street).field("city", city)
                .field("postcode", postcode).endObject();
    }

}
