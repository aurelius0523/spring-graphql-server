package com.aurelius.graph.configuration;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories("com.aurelius.graph.document")
public class DatabaseConfiguration {

    @Bean
    public MongoClient mongoClient() {
        MongoClient mongoClient = new MongoClient();
        return new MongoClient("localhost");
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoClient(), "graphql-server");
    }}
