package com.storyworld.service;

import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;

public interface UserInterfaceService {
	
	public Response getFavouritePlaces(Request request, Response response);
	
	public Response saveFavouritePlaces(Request request, Response response);
	
}
