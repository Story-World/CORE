package com.packt.storyworld.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.packt.storyworld.domain.json.Request;

import com.packt.storyworld.domain.json.Response;
import com.packt.storyworld.domain.sql.User;
import com.packt.storyworld.service.AuthorizationService;
import com.packt.storyworld.service.UserService;

@Controller
@RequestMapping(value = "user")
public class UserController {

//	@Autowired
//	private AuthorizationService authorizationService;
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> login(@RequestBody Request request) {
//		Response response = authorizationService.prepareResponse(request);
		System.out.println(request.getUser().getName());
		System.out.println(request.getUser().getPassword());
		Response response = new Response();
//		if(response!=null){
//			userService.login(request, response);
//		}

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> register(User user) {
		Response response = new Response();

		userService.register(user, response);

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

}
