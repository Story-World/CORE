package com.storyworld.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.storyworld.repository.sql.UserRepository;
import com.storyworld.service.UserService;

@Component
public class TokenScheduler {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Scheduled(fixedRate = 60000)
	public void removeToken() {
		userRepository.findByTokenNotNull().ifPresent(x -> x.forEach(userService::removeToken));
	}

}
