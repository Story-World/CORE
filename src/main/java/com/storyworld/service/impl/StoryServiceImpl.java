package com.storyworld.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.storyworld.domain.json.Message;
import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;
import com.storyworld.domain.json.enums.StatusMessage;
import com.storyworld.domain.sql.Story;
import com.storyworld.functionalInterface.JSONPrepare;
import com.storyworld.repository.sql.StoryRepository;
import com.storyworld.repository.sql.UserRepository;
import com.storyworld.service.StoryService;
import com.storyworld.service.helper.StoryServiceHelper;

@Service
public class StoryServiceImpl implements StoryService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StoryRepository storyRepository;

	@Autowired
	private StoryServiceHelper storyServiceHelper;

	private JSONPrepare<Story> jsonPrepare = (statusMessage, message, story, list,
			success) -> new Response<Story>(new Message(statusMessage, message), story, list, success);

	private static final Logger LOG = LoggerFactory.getLogger(StoryServiceImpl.class);

	@Override
	public Response<Story> addStory(Request request) {
		return userRepository.findByToken(request.getToken())
				.map(user -> storyServiceHelper.tryToSaveStroy(request.getStory(), user))
				.orElse(jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
	}

	@Override
	public Response<Story> getStory(Long id) {
		return Optional.ofNullable(storyRepository.findOne(id)).map(story -> storyServiceHelper.getStoryContent(story))
				.orElse(jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
	}

	@Override
	public Response<Story> getStories(int page, int size) {
		return Optional.ofNullable(storyRepository.findAll(new PageRequest(page, size)))
				.map(stories -> jsonPrepare.prepareResponse(StatusMessage.SUCCESS, "STORY_CRT", null,
						stories.getContent(), true))
				.orElse(jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
	}

}
