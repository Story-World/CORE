package com.packt.storyworld.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.packt.storyworld.repository.StoryRepository;

@Repository
public class StoryRepositoryImpl implements StoryRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

}
