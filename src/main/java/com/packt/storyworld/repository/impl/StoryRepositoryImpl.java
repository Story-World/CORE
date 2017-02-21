package com.packt.storyworld.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.packt.storyworld.domain.mongo.Story;

public class StoryRepositoryImpl {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(StoryRepositoryImpl.class);

	@Autowired
	private MongoTemplate mongoTemplate;

	public void saveStory(Story story) {
		mongoTemplate.save(story);
	}

}
