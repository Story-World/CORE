package com.storyworld.service.impl;

import org.springframework.stereotype.Service;

import com.storyworld.domain.json.Message;
import com.storyworld.domain.json.Response;
import com.storyworld.domain.json.StatusMessage;
import com.storyworld.domain.sql.User;
import com.storyworld.service.JSONService;

@Service
public class JSONServiceImpl implements JSONService {

	@Override
	public void prepareResponse(Response response, StatusMessage messageStatus, String messageString, User user,
			boolean success) {
		Message message = new Message();
		message.setMessage(messageString);
		message.setStatus(messageStatus);
		response.setUser(user);
		response.setSuccess(success);
		response.setMessage(message);
	}
}
