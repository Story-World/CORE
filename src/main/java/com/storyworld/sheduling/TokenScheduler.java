package com.storyworld.sheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.storyworld.repository.sql.UserRepository;

@Component
public class TokenScheduler {

	@Autowired
	private UserRepository userRepository;

	@Scheduled(fixedRate = 5000)
	public void removeToken() {
		System.out.println(userRepository.findOne(1L));
	}

}
