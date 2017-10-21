package com.storyworld.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.storyworld.annotations.Secure;
import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;
import com.storyworld.domain.sql.Story;
import com.storyworld.service.AuthorizationService;
import com.storyworld.service.StoryService;

@RestController
@RequestMapping(value = "story")
public class StoryController {

	@Autowired
	private AuthorizationService authorizationService;

	@Autowired
	private StoryService storyService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Response<Story>> getStoryByUser(@RequestHeader("Token") String token) {
		return new ResponseEntity<Response<Story>>(storyService.getStoriesByUser(token), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Response<Story>> getStory(@PathVariable(value = "id") Long id) {
		return new ResponseEntity<Response<Story>>(storyService.getStory(id), HttpStatus.OK);
	}

	@RequestMapping(value = "/{page}/{size}", method = RequestMethod.GET)
	public ResponseEntity<Response<Story>> getStories(@PathVariable(value = "page") int page,
			@PathVariable(value = "size") int size) {
		return new ResponseEntity<Response<Story>>(storyService.getStories(page, size, null), HttpStatus.OK);
	}

	@RequestMapping(value = "/{page}/{size}/{text}", method = RequestMethod.GET)
	public ResponseEntity<Response<Story>> searchStories(@PathVariable(value = "page") int page,
			@PathVariable(value = "size") int size, @PathVariable(value = "text") String text) {
		return new ResponseEntity<Response<Story>>(storyService.getStories(page, size, text), HttpStatus.OK);
	}

	@Secure()
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public ResponseEntity<Response<Story>> updateUser(@RequestBody Request request) {
		return authorizationService.checkAccess(request)
				? new ResponseEntity<Response<Story>>(storyService.addStory(request), HttpStatus.OK)
				: new ResponseEntity<Response<Story>>(new Response<Story>(), HttpStatus.UNAUTHORIZED);
	}
}
