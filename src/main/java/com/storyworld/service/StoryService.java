package com.storyworld.service;

import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;

public interface StoryService {

	public void add(Request request, Response response);

	public void get(Long id, Response response);

	public void getStories(int page, int size, Response response);

	public void update(Request request, Response response);

}
