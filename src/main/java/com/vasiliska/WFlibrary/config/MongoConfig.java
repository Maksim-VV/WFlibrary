package com.vasiliska.WFlibrary.config;

import com.github.mongobee.Mongobee;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import com.vasiliska.WFlibrary.changelog.DatabaseChangelog;

@Configuration
public class MongoConfig {

    @Value("${mongo.db.url}")
    private String MONGO_DB_URL;

    @Value(("${mongo.db.port:27017}"))
    private int MONGO_DB_PORT;

    @Value("${mongo.db.name}")
    private String MONGO_DB_NAME;

    @Bean
    public MongoClient reactiveMongoClient() {
        return MongoClients.create(MONGO_DB_URL + ":" + MONGO_DB_PORT);
    }

    @Bean
    public Mongobee mongobee(Environment environment) {
        Mongobee runner = new Mongobee(MONGO_DB_URL + ":" + MONGO_DB_PORT);
        runner.setDbName(MONGO_DB_NAME);
        runner.setChangeLogsScanPackage(DatabaseChangelog.class.getPackage().getName());
        runner.setSpringEnvironment(environment);
        return runner;
    }
}
