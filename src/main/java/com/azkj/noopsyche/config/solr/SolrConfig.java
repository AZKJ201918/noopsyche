package com.azkj.noopsyche.config.solr;

import com.azkj.noopsyche.common.intercepors.LoginInterceptor;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.util.NamedList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class SolrConfig {
    @Bean
    SolrServer getSolrServer() {
        return new SolrServer() {
            @Override
            public NamedList<Object> request(SolrRequest solrRequest) throws SolrServerException, IOException {
                return null;
            }

            @Override
            public void shutdown() {

            }
        };
    }
}
