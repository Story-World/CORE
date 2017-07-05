package com.storyworld.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.storyworld.domain.elastic.CommentContent;
import com.storyworld.domain.json.Response;
import com.storyworld.domain.json.enums.StatusMessage;
import com.storyworld.domain.sql.User;
import com.storyworld.service.JSONService;

@Service
public class JSONServiceImpl implements JSONService {

	@Override
	public void prepareResponseForUser(Response response, StatusMessage messageStatus, String messageString, User user,
			List<User> users, boolean success) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prepareResponseForComment(Response response, StatusMessage messageStatus, String messageString,
			List<CommentContent> commentsContent, CommentContent comment, boolean success) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prepareResponse(Response response, List<CommentContent> comments, User user, boolean success) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prepareSimpleResponse(Response response, boolean success, StatusMessage status, String messageText) {
		// TODO Auto-generated method stub
		
	}

}
