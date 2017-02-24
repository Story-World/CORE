package com.packt.storyworld.service.impl;

import com.packt.storyworld.domain.json.Request;
import com.packt.storyworld.domain.json.Response;
import com.packt.storyworld.service.AuthorizationService;

public class AuthorizationServiceImpl implements AuthorizationService {

	private void authorizeToken(String token){
		
	}
	
	@Override
	public Response prepareResponse(Request request) {
		authorizeToken(request.getToken());
		Response response = new Response();
		return response;
	}
}
