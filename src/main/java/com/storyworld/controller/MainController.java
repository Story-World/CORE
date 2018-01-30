package com.storyworld.controller;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.storyworld.annotations.Secure;
import com.storyworld.domain.json.Response;

@RestController
public class MainController {

	@Secure
	@RequestMapping
	public ResponseEntity<Response<Object>> defaultHandler() {
		return new ResponseEntity<Response<Object>>(new Response<Object>(), HttpStatus.OK);
	}
}
