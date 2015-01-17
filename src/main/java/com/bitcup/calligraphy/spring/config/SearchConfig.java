package com.bitcup.calligraphy.spring.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 * @author bitcup
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.bitcup.calligraphy")
public class SearchConfig {

    private static final Logger logger = LoggerFactory.getLogger(SearchConfig.class);

    @Bean(name = "elasticsearchTemplate")
    public ElasticsearchOperations elasticSearchTemplate() {
        // create an embedded Node that acts as a node within our cluster
        // this node does not hold data ==> client=true
        // q: can this node be created from the elasticsearch.yml config file in the classpath, without need to specify cluster name, etc.
        /*
        Node node = nodeBuilder().loadConfigSettings(false).local(false).client(true).clusterName("callig").node();
        Client client = node.client();
        */
        /* here's another way of connecting
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", "callig")
                .put("node.data", false)
                .build();
        Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
        */
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("http.enabled", "false")
                .put("transport.tcp.port", "9300-9400")
                .put("discovery.zen.ping.multicast.enabled", "false")
                .put("discovery.zen.ping.unicast.hosts", "localhost").build();

        Node node = nodeBuilder().client(true).settings(settings).clusterName("callig").node();
        Client client = node.client();

        logger.info("ES client node: {}", client.settings().toDelimitedString(",".charAt(0)));
        return new ElasticsearchTemplate(client);
    }
}
