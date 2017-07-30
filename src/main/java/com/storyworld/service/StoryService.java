package com.storyworld.service;

import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;

public interface StoryService {

	public Response addStory(Request request);

	public Response getStory(Long id);

	public Response getStories(int page, int size);

}
