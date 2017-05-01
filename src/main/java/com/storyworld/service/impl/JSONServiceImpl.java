package com.storyworld.service.impl;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.storyworld.domain.elastic.CommentContent;
import com.storyworld.domain.json.Message;
import com.storyworld.domain.json.Response;
import com.storyworld.domain.json.StatusMessage;
import com.storyworld.domain.sql.User;
import com.storyworld.service.JSONService;

@Service
public class JSONServiceImpl implements JSONService {

	@Override
	public void prepareResponseForUser(Response response, StatusMessage messageStatus, String messageString, User user,
			Page<User> users, boolean success) {
		prepareMessage(response, messageStatus, messageString);
		response.setUser(user);
		response.setUsers(users);
		response.setSuccess(success);
	}

	@Override
	public void prepareResponseForComment(Response response, StatusMessage messageStatus, String messageString,
			Set<CommentContent> commentsContent, boolean success) {
		prepareMessage(response, messageStatus, messageString);
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
}
