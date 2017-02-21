package com.packt.storyworld.repository;

import com.packt.storyworld.domain.mongo.Story;

public interface StoryRepository {

	public void saveStory(Story story);

}
