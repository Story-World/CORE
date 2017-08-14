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
import com.storyworld.domain.sql.User;
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
	public ResponseEntity<Response<User>> get(@PathVariable(value = "id") Long id,
			@RequestHeader("Token") String token) {
		if (authorizationService.checkAccess(new Request(token)))
			return new ResponseEntity<Response<User>>(userService.getUser(new Request(token)), HttpStatus.OK);
		else
			return new ResponseEntity<Response<User>>(new Response<User>(), HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ResponseEntity<Response<User>> login(@RequestBody Request request) {
		return new ResponseEntity<Response<User>>(userService.login(request), HttpStatus.OK);
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public ResponseEntity<Response<User>> register(@RequestBody Request request) {
		return new ResponseEntity<Response<User>>(userService.register(request), HttpStatus.OK);
	}

	@RequestMapping(value = "restartPassword", method = RequestMethod.POST)
	public ResponseEntity<Response<User>> restart(@RequestBody Request request) {
		return new ResponseEntity<Response<User>>(userService.restartPassword(request), HttpStatus.OK);
	}

	@RequestMapping(value = "remindPassword", method = RequestMethod.PUT)
	public ResponseEntity<Response<User>> remindPassword(@RequestBody Request request) {
		return new ResponseEntity<Response<User>>(userService.remindPassword(request), HttpStatus.OK);
	}

	@RequestMapping(value = "confirmRegister", method = RequestMethod.POST)
	public ResponseEntity<Response<User>> confirmRegister(@RequestBody Request request) {
		return new ResponseEntity<Response<User>>(userService.confirmRegister(request), HttpStatus.OK);
	}

	@RequestMapping(value = "changePassword", method = RequestMethod.PUT)
	public ResponseEntity<Response<User>> changePassword(Request request) {
		return new ResponseEntity<Response<User>>(userService.changePassword(request), HttpStatus.OK);
	}

	@RequestMapping(value = "updateUser", method = RequestMethod.PUT)
	public ResponseEntity<Response<User>> updateUser(@RequestBody Request request) {
		if (authorizationService.checkAccessToUser(request))
			return new ResponseEntity<Response<User>>(userService.update(request), HttpStatus.OK);
		else
			return new ResponseEntity<Response<User>>(new Response<User>(), HttpStatus.UNAUTHORIZED);

	}

	@RequestMapping(value = "block", method = RequestMethod.PUT)
	public ResponseEntity<Response<User>> blockUser(@RequestBody Request request) {
		if (authorizationService.checkAccessAdmin(request))
			return new ResponseEntity<Response<User>>(userService.block(request), HttpStatus.OK);
		else
			return new ResponseEntity<Response<User>>(new Response<User>(), HttpStatus.UNAUTHORIZED);

	}

	@RequestMapping(value = "getUsers", method = RequestMethod.POST)
	public ResponseEntity<Response<User>> getUsers(@RequestBody Request request) {
		if (authorizationService.checkAccess(request))
			return new ResponseEntity<Response<User>>(userService.getUsers(request), HttpStatus.OK);
		else
			return new ResponseEntity<Response<User>>(new Response<User>(), HttpStatus.UNAUTHORIZED);

	}

	@RequestMapping(value = "logout", method = RequestMethod.POST)
	public ResponseEntity<Response<User>> logout(Request request) {
		return new ResponseEntity<Response<User>>(userService.logout(request), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Response<User>> delete(@PathVariable(value = "id") Long id,
			@RequestHeader("Token") String token) {
		if (authorizationService.checkAccessToUser(new Request(token)))
			return new ResponseEntity<Response<User>>(userService.delete(id), HttpStatus.OK);
		else
			return new ResponseEntity<Response<User>>(new Response<User>(), HttpStatus.UNAUTHORIZED);

	}

}
