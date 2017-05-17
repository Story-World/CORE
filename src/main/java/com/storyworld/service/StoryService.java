package com.storyworld.service;

import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;

public interface StoryService {

	public void addStory(Request request, Response response);

	public void getStory(Long id, Response response);

	public void getStories(int page, int size, Response response);

}
