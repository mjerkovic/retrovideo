package com.mlj.retrovideo.domain.account;

import static com.google.common.collect.FluentIterable.from;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.mlj.retrovideo.domain.repository.SearchFileRepository;
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
    private final SearchFileRepository searchFileRepository;

    @Autowired
    public ElasticAccountRepository(Client client, ObjectMapper objectMapper, SearchFileRepository searchFileRepository) {
        this.client = client;
        this.objectMapper = objectMapper;
        this.searchFileRepository = searchFileRepository;
    }

    public void createAccount(AccountCreated event) {
        try {
            addAccountToIndex(event.getAccountNo(), createContent(event.getAccountNo(), event.getTitle(), event.getFirstName(),
                    event.getLastName(), event.getDateOfBirth(), event.getStreet(), event.getCity(), event.getPostcode()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AccountList all() {
        return accountsForPage(1, searchFileRepository.createSearchFile(findAllAccountIds()));
    }

    public AccountList accountsForPage(int pageNo, String searchFile) {
        List<String> ids = searchFileRepository.getIdsFrom(searchFile);
        SearchResponse searchResponse = searchByIdsForPage(ids, pageNo);
        return new AccountList(pageNo, searchFile, ids.size(), accountsFromResponse(searchResponse));
    }

    private SearchResponse searchByIdsForPage(List<String> ids, int pageNo) {
        return client.prepareSearch("retrovideo").setTypes("accounts")
                .setQuery(QueryBuilders.idsQuery("accounts").addIds(idsForPage(ids, pageNo)))
                .addSort("lastName", SortOrder.ASC)
                .execute().actionGet();
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

    private String[] idsForPage(List<String> ids, int pageNo) {
        int min = (pageNo - 1) * 10;
        int max = Math.min(min + 10, ids.size());
        return ids.subList(min, max).toArray(new String[max - min]);
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

    private List<String> findAllAccountIds() {
        SearchResponse searchResponse = client.prepareSearch("retrovideo").setTypes("accounts")
                .setQuery(QueryBuilders.matchAllQuery()).setSize(numberOfAccountsInIndex())
                .addSort("lastName", SortOrder.ASC).execute().actionGet();
        return FluentIterable.<SearchHit>from(searchResponse.hits()).transform(new Function<SearchHit, String>() {
            @Override
            public String apply(SearchHit searchHit) {
                return searchHit.id();
            }
        }).toImmutableList();
    }

    private int numberOfAccountsInIndex() {
        return (int) client.prepareCount("retrovideo").setTypes("accounts")
                .setQuery(QueryBuilders.matchAllQuery()).execute().actionGet().count();
    }


}
