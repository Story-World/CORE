package com.storyworld.service;

import org.springframework.stereotype.Service;

import com.storyworld.domain.json.StatusMessage;
import com.storyworld.domain.sql.User;
import com.storyworld.domain.json.Response;

@Service
public interface JSONService {

	public void prepareResponse(Response response, StatusMessage messageStatus, String messageString, User user, boolean success);
	
}
