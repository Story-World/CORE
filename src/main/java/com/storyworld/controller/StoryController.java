package com.storyworld.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;
import com.storyworld.service.AuthorizationService;
import com.storyworld.service.StoryService;

@RestController
@RequestMapping(value = "story")
public class StoryController {

	@Autowired
	private AuthorizationService authorizationService;
	
	@Autowired
	private StoryService storyService;
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public ResponseEntity<Response> updateUser(@RequestBody Request request) {
		Response response = new Response();

		if (authorizationService.checkAccessToUser(request))
			storyService.addStory(request, response);
		else
			return new ResponseEntity<Response>(response, HttpStatus.UNAUTHORIZED);

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
}
