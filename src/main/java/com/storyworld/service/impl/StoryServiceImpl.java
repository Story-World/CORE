package com.storyworld.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;
import com.storyworld.domain.sql.Story;
import com.storyworld.domain.sql.User;
import com.storyworld.enums.StoryState;
import com.storyworld.repository.sql.StoryRepository;
import com.storyworld.repository.sql.UserRepository;
import com.storyworld.service.StoryService;

@Service
public class StoryServiceImpl implements StoryService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private StoryRepository storyRepository;
	
	@Override
	public void addStory(Request request, Response response) {
		User user = userRepository.findByToken(request.getToken());
		System.out.println(request.getStory());
		Story story = request.getStory();
		story.setAuthor(user);
		story.setState(StoryState.NEW);
		
		storyRepository.save(story);
		
	}
	
	@Override
	public void saveStory() {
		//storyRepository.save(story);
	}

}
