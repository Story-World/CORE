package com.storyworld.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.storyworld.domain.json.Message;
import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;
import com.storyworld.domain.json.enums.StatusMessage;
import com.storyworld.domain.sql.Story;
import com.storyworld.domain.sql.User;
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

	@Override
	public Response<Story> addStory(Request request) {
		return userRepository.findByToken(request.getToken())
				.map(user -> storyServiceHelper.tryToSaveStroy(request.getStory(), user))
				.orElseGet(() -> jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
	}

	@Override
	public Response<Story> getStory(Long id) {
		return Optional.ofNullable(storyRepository.findOne(id)).map(story -> storyServiceHelper.getStoryContent(story))
				.orElseGet(() -> jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
	}

	@Override
	public Response<Story> getStories(int page, int size, String text) {
		return Optional.ofNullable(text).map(textSearch -> {
			return Optional.ofNullable(storyRepository.findByName(text, new PageRequest(page, size)))
					.map(stories -> jsonPrepare.prepareResponse(null, null, null, stories.getContent(), true))
					.orElseGet(() -> jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null,
							false));
		}).orElse(Optional.ofNullable(storyRepository.findAll(new PageRequest(page, size)))
				.map(stories -> jsonPrepare.prepareResponse(null, null, null, stories.getContent(), true)).orElseGet(
						() -> jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false)));
	}

	@Override
	public Response<Story> getStoriesByUser(String token) {
		Optional<User> userGet;
		try {
			Long id = Long.valueOf(token);
			userGet = Optional.ofNullable(userRepository.findOne(id));
		} catch (Exception e) {
			userGet = userRepository.findByToken(token);
		}
		return userGet
				.map(user -> jsonPrepare.prepareResponse(null, null, null,
						storyRepository.findByAuthor(user, new PageRequest(0, 10)).getContent(), true))
				.orElseGet(() -> jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
	}

}
