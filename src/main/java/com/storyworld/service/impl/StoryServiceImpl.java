package com.storyworld.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storyworld.domain.elastic.Chat;
import com.storyworld.repository.elastic.ChatRepository;
import com.storyworld.service.StoryService;

@Service
public class StoryServiceImpl implements StoryService {

	/*@Autowired
	private StoryRepository storyRepository;*/
	
	@Override
	public void saveStory() {
		//storyRepository.save(story);
	}

}
