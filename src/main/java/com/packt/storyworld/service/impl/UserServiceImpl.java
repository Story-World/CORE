package com.packt.storyworld.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packt.storyworld.domain.User;
import com.packt.storyworld.repository.UserRepository;
import com.packt.storyworld.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void removeToken(User user) {
		if (ChronoUnit.HOURS.between(user.getTime(), LocalDateTime.now()) >= 2) {
			user.setTime(null);
			user.setToken(null);
			userRepository.update(user);
		}
	}

}
