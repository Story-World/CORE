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

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Response> get(@PathVariable(value = "id") Long id) {
		return new ResponseEntity<Response>(userService.get(id), HttpStatus.OK);
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ResponseEntity<Response> login(@RequestBody Request request) {
		return new ResponseEntity<Response>(userService.login(request), HttpStatus.OK);
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public ResponseEntity<Response> register(@RequestBody Request request) {
		return new ResponseEntity<Response>(userService.register(request), HttpStatus.OK);
	}

	@RequestMapping(value = "restartPassword", method = RequestMethod.POST)
	public ResponseEntity<Response> restart(@RequestBody Request request) {
		return new ResponseEntity<Response>(userService.restartPassword(request), HttpStatus.OK);
	}

	@RequestMapping(value = "remindPassword", method = RequestMethod.PUT)
	public ResponseEntity<Response> remindPassword(@RequestBody Request request) {
		return new ResponseEntity<Response>(userService.remindPassword(request), HttpStatus.OK);
	}

	@RequestMapping(value = "confirmRegister", method = RequestMethod.POST)
	public ResponseEntity<Response> confirmRegister(@RequestBody Request request) {
		return new ResponseEntity<Response>(userService.confirmRegister(request), HttpStatus.OK);
	}

	@RequestMapping(value = "changePassword", method = RequestMethod.PUT)
	public ResponseEntity<Response> changePassword(Request request) {
		return new ResponseEntity<Response>(userService.changePassword(request), HttpStatus.OK);
	}

	@RequestMapping(value = "updateUser", method = RequestMethod.PUT)
	public ResponseEntity<Response> updateUser(@RequestBody Request request) {
		if (authorizationService.checkAccessToUser(request))
			return new ResponseEntity<Response>(userService.update(request), HttpStatus.OK);
		else
			return new ResponseEntity<Response>(new Response(), HttpStatus.UNAUTHORIZED);

	}

	@RequestMapping(value = "block", method = RequestMethod.PUT)
	public ResponseEntity<Response> blockUser(@RequestBody Request request) {
		if (authorizationService.checkAccessAdmin(request))
			return new ResponseEntity<Response>(userService.block(request), HttpStatus.OK);
		else
			return new ResponseEntity<Response>(new Response(), HttpStatus.UNAUTHORIZED);

	}

	@RequestMapping(value = "getUser", method = RequestMethod.POST)
	public ResponseEntity<Response> getUser(@RequestBody Request request) {
		if (authorizationService.checkAccess(request))
			return new ResponseEntity<Response>(userService.getUser(request), HttpStatus.OK);
		else
			return new ResponseEntity<Response>(new Response(), HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "getUsers", method = RequestMethod.POST)
	public ResponseEntity<Response> getUsers(@RequestBody Request request) {
		if (authorizationService.checkAccess(request))
			return new ResponseEntity<Response>(userService.getUsers(request), HttpStatus.OK);
		else
			return new ResponseEntity<Response>(new Response(), HttpStatus.UNAUTHORIZED);

	}

	@RequestMapping(value = "logout", method = RequestMethod.POST)
	public ResponseEntity<Response> logout(Request request) {
		return new ResponseEntity<Response>(userService.logout(request), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Response> delete(@PathVariable(value = "id") Long id, @RequestHeader("Token") String token) {
		if (authorizationService.checkAccessToUser(new Request(token)))
			return new ResponseEntity<Response>(userService.delete(id), HttpStatus.OK);
		else
			return new ResponseEntity<Response>(new Response(), HttpStatus.UNAUTHORIZED);

	}

}
