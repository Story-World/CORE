package com.storyworld.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.storyworld.domain.sql.User;
import com.storyworld.repository.sql.UserRepository;

@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/")
	public String greeting() {
		System.out.println("==============================TWORZE===================");
		User systemUser = userRepository.findByName("SYSTEM_USER");
		if(systemUser==null){
			systemUser = new User();
			systemUser.setName("SYSTEM_USER");
			systemUser.setMail("sw@storyworld.com");
			systemUser.setPassword("asfafgagadgfadgaddsa");
			systemUser.setBlock(false);
			systemUser.setDelete(false);
		}
		userRepository.save(systemUser);
		return "welcome";
	}

}