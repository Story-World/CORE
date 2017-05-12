package com.storyworld.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.storyworld.domain.elastic.CommentContent;
import com.storyworld.domain.json.Message;
import com.storyworld.domain.json.Response;
import com.storyworld.domain.json.StatusMessage;
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

}
