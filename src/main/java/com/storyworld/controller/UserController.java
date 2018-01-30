package com.storyworld.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;
import com.storyworld.domain.sql.User;
import com.storyworld.service.AuthorizationService;
import com.storyworld.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthorizationService authorizationService;

	@PostMapping("/login")
	public ResponseEntity<Response<User>> login(@RequestBody Request request) {
		return new ResponseEntity<Response<User>>(userService.login(request), HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<Response<User>> register(@RequestBody Request request) {
		return new ResponseEntity<Response<User>>(userService.register(request), HttpStatus.OK);
	}

	@PostMapping("/restartPassword")
	public ResponseEntity<Response<User>> restart(@RequestBody Request request) {
		return new ResponseEntity<Response<User>>(userService.restartPassword(request), HttpStatus.OK);
	}

	@PutMapping("/remindPassword")
	public ResponseEntity<Response<User>> remindPassword(@RequestBody Request request) {
		return new ResponseEntity<Response<User>>(userService.remindPassword(request), HttpStatus.OK);
	}

	@PostMapping("/confirmRegister")
	public ResponseEntity<Response<User>> confirmRegister(@RequestBody Request request) {
		return new ResponseEntity<Response<User>>(userService.confirmRegister(request), HttpStatus.OK);
	}

	@PutMapping("/changePassword")
	public ResponseEntity<Response<User>> changePassword(Request request) {
		return new ResponseEntity<Response<User>>(userService.changePassword(request), HttpStatus.OK);
	}

	@PutMapping("/updateUser")
	public ResponseEntity<Response<User>> updateUser(@RequestBody Request request) {
		return authorizationService.checkAccessToUser(request)
				? new ResponseEntity<Response<User>>(userService.update(request), HttpStatus.OK)
				: new ResponseEntity<Response<User>>(new Response<User>(), HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/block")
	public ResponseEntity<Response<User>> blockUser(@RequestBody Request request) {
		return authorizationService.checkAccessAdmin(request)
				? new ResponseEntity<Response<User>>(userService.block(request), HttpStatus.OK)
				: new ResponseEntity<Response<User>>(new Response<User>(), HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/getUsers")
	public ResponseEntity<Response<User>> getUsers(@RequestBody Request request) {
		return authorizationService.checkAccess(request)
				? new ResponseEntity<Response<User>>(userService.getUsers(request), HttpStatus.OK)
				: new ResponseEntity<Response<User>>(new Response<User>(), HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/logout")
	public ResponseEntity<Response<User>> logout(@RequestBody Request request) {
		return new ResponseEntity<Response<User>>(userService.logout(request), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response<User>> delete(@PathVariable(value = "id") Long id,
			@RequestHeader("Token") String token) {
		return authorizationService.checkAccessToUser(new Request(token))
				? new ResponseEntity<Response<User>>(userService.delete(id), HttpStatus.OK)
				: new ResponseEntity<Response<User>>(new Response<User>(), HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response<User>> get(@PathVariable(value = "id") Long id,
			@RequestHeader("Token") String token) {
		return new ResponseEntity<Response<User>>(userService.getUser(new Request(token)), HttpStatus.OK);
	}

}
