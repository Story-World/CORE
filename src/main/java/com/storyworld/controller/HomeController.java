package com.storyworld.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.storyworld.domain.sql.Role;
import com.storyworld.domain.sql.User;
import com.storyworld.repository.sql.RoleRepository;
import com.storyworld.repository.sql.UserRepository;

@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping("/")
	public String greeting() {
		LOG.info("Create SYSTEM_USER and roles");
		User systemUser = userRepository.findByName("SYSTEM_USER");
		if (systemUser == null) {
			systemUser = new User();
			systemUser.setName("SYSTEM_USER");
			systemUser.setMail("sw@storyworld.com");
			systemUser.setPassword("asfafgagadgfadgaddsa");
			systemUser.setBlock(false);
			systemUser.setDelete(false);
			userRepository.save(systemUser);
		}

		Role role = roleRepository.findOne(1L);
		if (role == null) {
			role = new Role("USER");
			roleRepository.save(role);
		}

		role = roleRepository.findOne(2L);
		if (role == null) {
			role = new Role("ADMIN");
			roleRepository.save(role);
		}
		return "welcome";
	}

}