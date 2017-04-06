package com.storyworld.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.storyworld.domain.mongo.Story;

public interface StoryRepository extends MongoRepository<Story, Long> {

}
