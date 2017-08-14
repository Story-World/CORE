package com.storyworld.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.storyworld.domain.elastic.StoryContent;
import com.storyworld.domain.json.Message;
import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;
import com.storyworld.domain.json.enums.StatusMessage;
import com.storyworld.domain.sql.Story;
import com.storyworld.functionalInterface.JSONPrepare;
import com.storyworld.repository.elastic.StoryContentRepository;
import com.storyworld.repository.sql.StoryRepository;
import com.storyworld.repository.sql.UserRepository;
import com.storyworld.service.StoryService;

@Service
public class StoryServiceImpl implements StoryService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StoryRepository storyRepository;

	@Autowired
	private StoryContentRepository storyContentRepository;

	private JSONPrepare<Story> jsonPrepare = (statusMessage, message, story, list,
			success) -> new Response<Story>(new Message(statusMessage, message), story, list, success);

	private static final Logger LOG = LoggerFactory.getLogger(StoryServiceImpl.class);

	@Override
	public Response<Story> addStory(Request request) {
		return userRepository.findByToken(request.getToken()).map(user -> {
			Story story = request.getStory();
			story.setAuthor(user);
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
				user.setLastActionTime(LocalDateTime.now());
				userRepository.save(user);
				return jsonPrepare.prepareResponse(StatusMessage.SUCCESS, "STORY_CRT", null, null, true);
			} catch (Exception e) {
				LOG.error(e.toString());
				return jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false);
			}
		}).orElse(jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
	}

	@Override
	public Response<Story> getStory(Long id) {
		return Optional.ofNullable(storyRepository.findOne(id)).map(story -> {
			return Optional.ofNullable(storyContentRepository.findOne(story.getContentId())).map(storyContent -> {
				story.setPages(storyContent.getPages());
				return jsonPrepare.prepareResponse(StatusMessage.SUCCESS, "STORY_CRT", story, null, true);
			}).orElseGet(() -> jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
		}).orElseGet(() -> jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
	}

	@Override
	public Response<Story> getStories(int page, int size) {
		return Optional.ofNullable(storyRepository.findAll(new PageRequest(page, size)))
				.map(stories -> jsonPrepare.prepareResponse(StatusMessage.SUCCESS, "STORY_CRT", null,
						stories.getContent(), true))
				.orElseGet(() -> jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
	}

}
