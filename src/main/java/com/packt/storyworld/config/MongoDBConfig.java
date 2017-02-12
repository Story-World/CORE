package com.packt.storyworld.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories(basePackages = "storyworld.db")
public class MongoDBConfig extends AbstractMongoConfiguration {

	@Override
	protected String getDatabaseName() {
		return "storyworld";
	}

	@Override
	public Mongo mongo() {
		return new MongoClient();
	}

}
