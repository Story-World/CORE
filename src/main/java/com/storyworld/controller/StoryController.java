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
		if (authorizationService.checkAccess(request))
			return new ResponseEntity<Response>(storyService.addStory(request), HttpStatus.OK);
		else
			return new ResponseEntity<Response>(new Response(), HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Response> getStory(@PathVariable(value = "id") Long id) {
		return new ResponseEntity<Response>(storyService.getStory(id), HttpStatus.OK);
	}

	@RequestMapping(value = "/{page}/{size}", method = RequestMethod.GET)
	public ResponseEntity<Response> getStories(@PathVariable(value = "page") int page,
			@PathVariable(value = "size") int size) {
		return new ResponseEntity<Response>(storyService.getStories(page, size), HttpStatus.OK);
	}

}
