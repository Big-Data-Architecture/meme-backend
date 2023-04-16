package com.bda.userservice;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;

public class ElasticConfig {

    private static ElasticConfig instance = null;
    private static ElasticsearchClient client = null;

    private ElasticConfig() {
        BasicCredentialsProvider credsProv = new BasicCredentialsProvider();
        credsProv.setCredentials(
                AuthScope.ANY, new UsernamePasswordCredentials("elastic", "vI21oY4NSX9mdez7f0qJpXFj")
        );
        RestClient restClient = RestClient.builder(new HttpHost("meme-image-search.es.us-east1.gcp.elastic-cloud.com", 9243, "https"))
                .setHttpClientConfigCallback(hc ->
                        hc.setDefaultCredentialsProvider(credsProv)
                ).build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        client = new ElasticsearchClient(transport);
    }

    public static synchronized ElasticsearchClient getClientInstance() {
        if (instance == null || client == null) {
            try {
                instance = new ElasticConfig();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return client;
    }

}
