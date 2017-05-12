package com.storyworld.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.storyworld.domain.json.StatusMessage;
import com.storyworld.domain.sql.Story;
import com.storyworld.domain.sql.User;
import com.storyworld.domain.elastic.CommentContent;
import com.storyworld.domain.json.Response;

@Service
public interface JSONService {

	public void prepareResponseForUser(Response response, StatusMessage messageStatus, String messageString, User user,
			List<User> users, boolean success);

	public void prepareResponseForComment(Response response, StatusMessage messageStatus, String messageString,
			List<CommentContent> commentsContent, CommentContent comment, boolean success);

	public void prepareResponse(Response response, List<CommentContent> comments, List<Story> stories, User user, boolean success);

	public void prepareErrorResponse(Response response, String messageString);
	
	public void prepareSimpleResponse(Response response, boolean success, StatusMessage status, String messageText);

}
