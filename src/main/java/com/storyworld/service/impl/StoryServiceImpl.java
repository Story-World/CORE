package com.storyworld.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.storyworld.domain.elastic.StoryContent;
import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;
import com.storyworld.domain.json.StatusMessage;
import com.storyworld.domain.sql.Story;
import com.storyworld.domain.sql.User;
import com.storyworld.enums.StoryState;
import com.storyworld.repository.elastic.StoryContentRepository;
import com.storyworld.repository.sql.StoryRepository;
import com.storyworld.repository.sql.UserRepository;
import com.storyworld.service.JSONService;
import com.storyworld.service.StoryService;

@Service
public class StoryServiceImpl implements StoryService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StoryRepository storyRepository;
	
	@Autowired
	private StoryContentRepository storyContentRepository;
	
	@Autowired
	private JSONService jsonService;

	private static final Logger LOG = LoggerFactory.getLogger(StoryServiceImpl.class);

	@Override
	public void addStory(Request request, Response response) {
		User user = userRepository.findByToken(request.getToken());
		Story story = request.getStory();
		story.setAuthor(user);
		story.setState(StoryState.NEW);
		story.setDate(LocalDateTime.now());
		StoryContent storyContent = new StoryContent();
		List<String> pages = new ArrayList<String>();
		pages.add(story.getRawText());
		storyContent.setPages(pages);
		storyContent.setTitle(story.getTitle());
		storyContent = storyContentRepository.save(storyContent);
		story.setContentId(storyContent.getId());
		try {
			storyRepository.save(story);
		} catch (Exception e) {
			LOG.error(e.toString());
		}
		jsonService.prepareSimpleResponse(response, true, StatusMessage.SUCCESS, "STORY_CRT");
	}

	@Override
	public void getStory(Long id, Response response) {
		Story story = storyRepository.findOne(id);
		if(story!=null){
			StoryContent storyContent = storyContentRepository.findOne(story.getContentId());
			if(storyContent!=null){
				story.setPages(storyContent.getPages());
				response.setStory(story);
				response.setSuccess(true);
			}
		}
		
	}
	
	@Override
	public void getStories(int page, int size, Response response) {
		Page<Story> storiesPage = storyRepository.findByStateIn(StoryState.NEW,
				new PageRequest(page, size));
		List<Story> stories = new ArrayList<Story>();
		for(Story story: storiesPage){
			stories.add(story);
		}
		response.setStories(stories);
		response.setSuccess(true);
	}

}
