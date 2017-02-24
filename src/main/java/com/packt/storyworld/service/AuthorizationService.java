package com.packt.storyworld.service;

import com.packt.storyworld.domain.json.Request;
import com.packt.storyworld.domain.json.Response;

public interface AuthorizationService {

	public Response prepareResponse(Request request);
}
