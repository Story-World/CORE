package com.packt.storyworld.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packt.storyworld.domain.mongo.Story;
import com.packt.storyworld.repository.StoryRepository;
import com.packt.storyworld.service.StoryService;

@Service
public class StoryServiceImpl implements StoryService {

	@Autowired
	private StoryRepository storyRepository;

	@Override
	public void saveStory(Story story) {
		storyRepository.saveStory(story);
	}

}
