package com.storyworld.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storyworld.domain.mongo.Story;
import com.storyworld.repository.mongo.StoryRepository;
import com.storyworld.service.StoryService;

@Service
public class StoryServiceImpl implements StoryService {

	@Autowired
	private StoryRepository storyRepository;

	@Override
	public void saveStory(Story story) {
		storyRepository.save(story);
	}

}
