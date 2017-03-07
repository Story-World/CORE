package com.storyworld.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.storyworld.domain.json.Response;
import com.storyworld.service.UserService;

@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "")
	public String greeting(){
		return "welcome";
	}
	
	@RequestMapping(value = "getUsers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getUsers(){
		Response response = new Response();

		response = userService.getUsers(response);

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}