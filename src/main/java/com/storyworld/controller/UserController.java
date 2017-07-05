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
@RequestMapping(value = "user")
public class UserController {

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Response> getDetail(@PathVariable(value = "id") Long id) {
		Response response = new Response();

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ResponseEntity<Response> get(@PathVariable(value = "id") Long id, Request request) {
		Response response = new Response();

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Response> delete(@PathVariable(value = "id") Long id, @RequestHeader("Token") String token) {
		Response response = new Response();

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ResponseEntity<Response> login(Request request) {
		Response response = new Response();

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public ResponseEntity<Response> register(Request request) {
		Response response = new Response();

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "restartPassword", method = RequestMethod.POST)
	public ResponseEntity<Response> restart(Request request) {
		Response response = new Response();

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "remindPassword", method = RequestMethod.PUT)
	public ResponseEntity<Response> remindPassword(Request request) {
		Response response = new Response();

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "changePassword", method = RequestMethod.PUT)
	public ResponseEntity<Response> changePassword(Request request) {
		Response response = new Response();

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "confirmRegister", method = RequestMethod.POST)
	public ResponseEntity<Response> confirmRegister(Request request) {
		Response response = new Response();

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "update", method = RequestMethod.PUT)
	public ResponseEntity<Response> update(Request request) {
		Response response = new Response();

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "block", method = RequestMethod.PUT)
	public ResponseEntity<Response> block(Request request) {
		Response response = new Response();

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "getUsers", method = RequestMethod.POST)
	public ResponseEntity<Response> getUsers(Request request) {
		Response response = new Response();

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "logout", method = RequestMethod.POST)
	public ResponseEntity<Response> logout(Request request) {
		Response response = new Response();

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

}
