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
import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;
import com.storyworld.domain.json.enums.StatusMessage;
import com.storyworld.domain.sql.Story;
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
	public Response addStory(Request request) {
		return userRepository.findByToken(request.getToken()).map(x -> {
			Story story = request.getStory();
			story.setAuthor(x);
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
				x.setLastActionTime(LocalDateTime.now());
				userRepository.save(x);
				return jsonService.prepareSimpleResponse(true, StatusMessage.SUCCESS, "STORY_CRT");
			} catch (Exception e) {
				LOG.error(e.toString());
				return jsonService.prepareErrorResponse("INCORRECT_DATA");
			}
		}).orElse(jsonService.prepareErrorResponse("INCORRECT_DATA"));
	}

	@Override
	public Response getStory(Long id) {
		return Optional.ofNullable(storyRepository.findOne(id)).map(story -> {
			return Optional.ofNullable(storyContentRepository.findOne(story.getContentId())).map(storyContent -> {
				story.setPages(storyContent.getPages());
				return jsonService.prepareResponseForStroy(StatusMessage.SUCCESS, null, story, storyContent, null,
						true);
			}).orElseGet(() -> jsonService.prepareErrorResponse("INCORRECT_DATA"));
		}).orElseGet(() -> jsonService.prepareErrorResponse("INCORRECT_DATA"));
	}

	@Override
	public Response getStories(int page, int size) {
		return Optional.ofNullable(storyRepository.findAll(new PageRequest(page, size)))
				.map(stories -> jsonService.prepareResponseForStroy(StatusMessage.SUCCESS, null, null, null,
						stories.getContent(), true))
				.orElseGet(() -> jsonService.prepareErrorResponse("INCORRECT_DATA"));
	}

}
