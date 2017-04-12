package com.storyworld.config;

import java.net.InetAddress;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories
public class ElasticSearchConfig {

	private static final String PROPERTY_ELASTIC_HOST = "localhost";
	private static final int PROPERTY_ELASTIC_PORT = 9300;
	private static final String PROPERTY_ELASTIC_CLUSTER_NAME = "elasticsearch";

	@Bean
	public ElasticsearchOperations elasticsearchTemplate() throws Exception {
		return new ElasticsearchTemplate(client());
	}

	@Bean
	public Client client() throws Exception {
		Settings esSettings = Settings.settingsBuilder().put("cluster.name", PROPERTY_ELASTIC_CLUSTER_NAME).build();

		// https://www.elastic.co/guide/en/elasticsearch/guide/current/_transport_client_versus_node_client.html
		return TransportClient.builder().settings(esSettings).build().addTransportAddress(
				new InetSocketTransportAddress(InetAddress.getByName(PROPERTY_ELASTIC_HOST), PROPERTY_ELASTIC_PORT));
	}

}
