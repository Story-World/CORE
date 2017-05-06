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

import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;
import com.storyworld.service.AuthorizationService;
import com.storyworld.service.UserService;

@RestController
@RequestMapping(value = "user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthorizationService authorizationService;

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ResponseEntity<Response> login(@RequestBody Request request) {
		Response response = new Response();

		userService.login(request, response);

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public ResponseEntity<Response> register(@RequestBody Request request) {
		Response response = new Response();

		userService.register(request, response);

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "restartPassword", method = RequestMethod.POST)
	public ResponseEntity<Response> restart(@RequestBody Request request) {
		Response response = new Response();

		userService.restartPassword(request, response);

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "remindPassword", method = RequestMethod.PUT)
	public ResponseEntity<Response> remindPassword(@RequestBody Request request) {
		Response response = new Response();

		userService.remindPassword(request, response);

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "confirmRegister", method = RequestMethod.POST)
	public ResponseEntity<Response> confirmRegister(@RequestBody Request request) {
		Response response = new Response();

		userService.confirmRegister(request, response);

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "changePassword", method = RequestMethod.PUT)
	public ResponseEntity<Response> changePassword(@RequestBody Request request) {
		Response response = new Response();

		userService.changePassword(request, response);

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "updateUser", method = RequestMethod.PUT)
	public ResponseEntity<Response> updateUser(@RequestBody Request request) {
		Response response = new Response();

		if (authorizationService.checkAccessToUser(request))
			userService.update(request, response);
		else
			return new ResponseEntity<Response>(response, HttpStatus.UNAUTHORIZED);

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "getUser", method = RequestMethod.POST)
	public ResponseEntity<Response> getUser(@RequestBody Request request) {
		Response response = new Response();

		if (authorizationService.checkAccess(request))
			userService.getUser(request, response);
		else
			return new ResponseEntity<Response>(response, HttpStatus.UNAUTHORIZED);

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "getUsers", method = RequestMethod.POST)
	public ResponseEntity<Response> getUsers(@RequestBody Request request) {
		Response response = new Response();

		if (authorizationService.checkAccess(request))
			userService.getUsers(request, response);
		else
			return new ResponseEntity<Response>(response, HttpStatus.UNAUTHORIZED);

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "logout", method = RequestMethod.POST)
	public ResponseEntity<Response> logout(@RequestBody Request request) {
		Response response = new Response();

		userService.logout(request, response);

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Response> delete(@PathVariable(value = "id") Long id, @RequestHeader("Token") String token) {
		Response response = new Response();

		if (authorizationService.checkAccessToUser(new Request(token)))
			userService.delete(id, response);
		else
			return new ResponseEntity<Response>(response, HttpStatus.UNAUTHORIZED);

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}
