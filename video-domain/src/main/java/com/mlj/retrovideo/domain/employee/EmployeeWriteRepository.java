package com.mlj.retrovideo.domain.employee;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeWriteRepository {

    private final Client client;
    private final ObjectMapper objectMapper;

    @Autowired
    public EmployeeWriteRepository(Client client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public void addEmployee(EmployeeAdded event) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("employeeId", event.getEmployeeId())
                    .field("firstname", event.getFirstName()).field("lastname", event.getLastName())
                    .field("username", event.getUsername()).field("password", event.getPassword())
                    .field("administrator", event.isAdministrator()).endObject();
            client.prepareIndex("retrovideo", "employees", event.getEmployeeId()).setSource(builder).setRefresh(true)
                    .execute().actionGet();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
