package com.storyworld.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.storyworld.domain.elastic.CommentContent;
import com.storyworld.domain.elastic.StoryContent;
import com.storyworld.domain.json.Message;
import com.storyworld.domain.json.Response;
import com.storyworld.domain.json.enums.StatusMessage;
import com.storyworld.domain.sql.Story;
import com.storyworld.domain.sql.User;
import com.storyworld.service.JSONService;

@Service
public class JSONServiceImpl implements JSONService {

	@Override
	public void prepareResponseForUser(Response response, StatusMessage messageStatus, String messageString, User user,
			List<User> users, boolean success) {
		prepareMessage(response, messageStatus, messageString);
		response.setUser(user);
		response.setUsers(users);
		response.setSuccess(success);
	}

	@Override
	public void prepareResponseForComment(Response response, StatusMessage messageStatus, String messageString,
			List<CommentContent> commentsContent, CommentContent comment, boolean success) {
		prepareMessage(response, messageStatus, messageString);
		response.setComment(comment);
		response.setComments(commentsContent);
		response.setSuccess(success);
	}

	@Override
	public void prepareErrorResponse(Response response, String messageString) {
		response.setMessage(new Message(StatusMessage.ERROR, messageString));
		response.setSuccess(false);
	}

	@Override
	public void prepareSimpleResponse(Response response, boolean success, StatusMessage status, String messageText) {
		response.setMessage(new Message(status, messageText));
		response.setSuccess(success);
	}

	private void prepareMessage(Response response, StatusMessage messageStatus, String messageString) {
		if (messageStatus != null && messageString != null)
			response.setMessage(new Message(messageStatus, messageString));
	}

	@Override
	public void prepareResponse(Response response, List<CommentContent> comments, List<Story> stories, User user,
			boolean success) {
		response.setUser(user);
		response.setComments(comments);
		response.setStories(stories);
		response.setSuccess(success);
	}

	@Override
	public Response prepareResponseForUser(StatusMessage messageStatus, String messageString, User user,
			List<User> users, boolean success) {
		Response response = new Response();
		prepareMessage(response, messageStatus, messageString);
		response.setUser(user);
		response.setUsers(users);
		response.setSuccess(success);
		return response;
	}

	@Override
	public Response prepareResponseForComment(StatusMessage messageStatus, String messageString,
			List<CommentContent> commentsContent, CommentContent comment, boolean success) {
		Response response = new Response();
		prepareMessage(response, messageStatus, messageString);
		response.setComment(comment);
		response.setComments(commentsContent);
		response.setSuccess(success);
		return response;
	}

	@Override
	public Response prepareResponse(List<CommentContent> comments, List<Story> stories, User user, boolean success) {
		Response response = new Response();
		response.setUser(user);
		response.setComments(comments);
		response.setStories(stories);
		response.setSuccess(success);
		return response;
	}

	@Override
	public Response prepareErrorResponse(String messageString) {
		Response response = new Response();
		response.setMessage(new Message(StatusMessage.ERROR, messageString));
		response.setSuccess(false);
		return response;
	}

	@Override
	public Response prepareSimpleResponse(boolean success, StatusMessage status, String messageText) {
		Response response = new Response();
		response.setMessage(new Message(status, messageText));
		response.setSuccess(success);
		return response;
	}

	@Override
	public Response prepareResponseForStroy(StatusMessage status, String messageText, Story story,
			StoryContent storyContent, List<Story> content, boolean success) {
		Response response = new Response();
		response.setMessage(new Message(status, messageText));
		response.setSuccess(success);
		response.setStory(story);
		response.setStoryContent(storyContent);
		return response;
	}

}
