package com.mlj.retrovideo.domain.video;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class ElasticBootstrap implements InitializingBean, DisposableBean {

    private Client client;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Creating client...");
        client = NodeBuilder.nodeBuilder().local(true).node().client();
        System.out.println("...Client created");
    }

    @Override
    public void destroy() throws Exception {
        client.close();
    }

    public Client getClient() {
        return client;
    }

}
