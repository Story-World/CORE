package com.storyworld.controller;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.storyworld.domain.json.Response;

@RestController
public class MainController {

	@RequestMapping
	public ResponseEntity<Response<?>> defaultHandler() {
		return new ResponseEntity<Response<?>>(new Response<Object>(), HttpStatus.OK);
	}
}
