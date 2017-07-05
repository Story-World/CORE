package com.storyworld.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;

@RestController
@RequestMapping(value = "story")
public class StoryController {

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public ResponseEntity<Response> add(Request request) {
		Response response = new Response();

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Response> get(@PathVariable(value = "id") Long id) {
		Response response = new Response();

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/{page}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<Response> getStories(@PathVariable(value = "page") int page,
			@PathVariable(value = "pageSize") int pageSize) {
		Response response = new Response();

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Response> update(@PathVariable(value = "id") Long id) {
		Response response = new Response();

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

}
