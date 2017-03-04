package com.storyworld.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;
import com.storyworld.domain.sql.User;
import com.storyworld.repository.sql.UserRepository;
import com.storyworld.service.AuthorizationService;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

	@Autowired
	private UserRepository userRepository;

	private void authorizeToken(String token) {

	}

	@Override
	public Response prepareResponse(Request request) {
		authorizeToken(request.getToken());
		Response response = new Response();
		return response;
	}

	@Override
	public User checkAccessToUser(String token) {
		User user = userRepository.findByToken(token);
		// if(user==null)
		// throw new AccessDeniedException("AccesDenied");
		return user;
	}
}
