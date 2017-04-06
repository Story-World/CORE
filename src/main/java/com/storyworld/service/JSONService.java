package com.storyworld.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.storyworld.domain.json.StatusMessage;
import com.storyworld.domain.sql.User;
import com.storyworld.domain.json.Response;

@Service
public interface JSONService {

	public void prepareResponse(Response response, StatusMessage messageStatus, String messageString, User user,
			List<User> users, boolean success);

}
