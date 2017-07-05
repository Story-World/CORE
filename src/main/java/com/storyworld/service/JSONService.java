package com.storyworld.service;

import java.util.List;

import com.storyworld.domain.elastic.CommentContent;
import com.storyworld.domain.json.Response;
import com.storyworld.domain.json.enums.StatusMessage;
import com.storyworld.domain.sql.User;

public interface JSONService {

	public void prepareResponseForUser(Response response, StatusMessage messageStatus, String messageString, User user,
			List<User> users, boolean success);

	public void prepareResponseForComment(Response response, StatusMessage messageStatus, String messageString,
			List<CommentContent> commentsContent, CommentContent comment, boolean success);

	public void prepareResponse(Response response, List<CommentContent> comments, User user, boolean success);

	public void prepareSimpleResponse(Response response, boolean success, StatusMessage status, String messageText);

}
