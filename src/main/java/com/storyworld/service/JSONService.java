package com.storyworld.service;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.storyworld.domain.json.StatusMessage;
import com.storyworld.domain.sql.User;
import com.storyworld.domain.elastic.CommentContent;
import com.storyworld.domain.json.Response;

@Service
public interface JSONService {

	public void prepareResponseForUser(Response response, StatusMessage messageStatus, String messageString, User user,
			Page<User> users, boolean success);

	public void prepareResponseForComment(Response response, StatusMessage messageStatus, String messageString,
			Set<CommentContent> commentsContent, boolean success);

	public void prepareErrorResponse(Response response, String messageString);

}
