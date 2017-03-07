package com.storyworld.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;
import com.storyworld.service.UserInterfaceService;

@RestController
@RequestMapping(value="userInterface")
public class UserInterfaceController {
	
	@Autowired
	private UserInterfaceService userInterfaceService;
	
	@RequestMapping(value = "favouritePlaces", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getFavouritePlaces(@RequestBody Request request){
		Response response = new Response();
		
		response = userInterfaceService.getFavouritePlaces(request, response);
		
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "favouritePlaces", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> saveFavouritePlaces(@RequestBody Request request){
		Response response = new Response();
		
		response = userInterfaceService.saveFavouritePlaces(request, response);
		
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
}
