package com.storyworld.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;

@RestController
@RequestMapping(value = "comment")
public class CommentController {

	@RequestMapping(value = "/{idStory}/{page}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<Response> getCommetsByStory(@PathVariable(value = "idStory") Long idStory,
			@PathVariable(value = "page") int page, @PathVariable(value = "pageSize") int pageSize) {
		Response response = new Response();
		
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Response> add(Request request) {
		Response response = new Response();

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Response> update(Request request) {
		Response response = new Response();

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Response> delete(@PathVariable(value = "id") String _id,
			@RequestHeader("Token") String token) {
		Response response = new Response();

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "like", method = RequestMethod.POST)
	public ResponseEntity<Response> like(Request request) {
		Response response = new Response();

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

}
