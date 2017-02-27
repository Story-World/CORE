package com.packt.storyworld.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.packt.storyworld.domain.mongo.Story;

public interface StoryRepository extends MongoRepository<Story, Long>{

}
