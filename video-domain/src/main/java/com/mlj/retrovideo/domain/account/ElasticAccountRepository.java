package com.mlj.retrovideo.domain.account;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;

import com.mlj.retrovideo.domain.repository.SearchFileRepository;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
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
