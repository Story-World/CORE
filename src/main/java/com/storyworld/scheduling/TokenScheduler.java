package com.storyworld.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.storyworld.repository.sql.UserRepository;

@Component
public class TokenScheduler {

	@Autowired
	private UserRepository userRepository;

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Scheduled(fixedRate = 5000)
	public void removeToken() {
		LOG.info(userRepository.findOne(1L).toString());
	}
}
