package com.storyworld.service;

import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;

public interface CommentService {

	public void get(Request request, Response response);

	public void save(Request request, Response response);

	public void update(Request request, Response response);

	public void delete(Request request, Response response);

}
