package com.packt.storyworld.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories(basePackages = "storyworld.db")
public class MongoDBConfig extends AbstractMongoConfiguration {

	private static final String PROPERTY_NAME_DATABASE_NAME = "storyworld";

	@Override
	protected String getDatabaseName() {
		return PROPERTY_NAME_DATABASE_NAME;
	}

	@Override
	public Mongo mongo() {
		return new MongoClient();
	}

}
