package com.storyworld.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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

		if (authorizationService.checkAccess(request))
			storyService.addStory(request, response);
		else
			return new ResponseEntity<Response>(response, HttpStatus.UNAUTHORIZED);

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Response> getStory(@PathVariable(value = "id") Long id) {
		Response response = new Response();

		storyService.getStory(id, response);
		
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/{page}/{size}", method = RequestMethod.GET)
	public ResponseEntity<Response> getStories(@PathVariable(value = "page") int page,
			@PathVariable(value = "size") int size) {
		Response response = new Response();

		storyService.getStories(page, size, response);
		
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

}
