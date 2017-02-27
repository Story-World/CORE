package com.packt.storyworld.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.packt.storyworld.domain.json.Request;
import com.packt.storyworld.domain.json.Response;
import com.packt.storyworld.service.UserService;

@RestController
@RequestMapping(value = "user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> login(@RequestBody Request request) {
		Response response = new Response();

		userService.login(request.getUser(), response);

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> register(@RequestBody Request request) {
		Response response = new Response();

		userService.register(request.getUser(), response);

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

}
