package com.packt.storyworld.scheduling;

import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokenScheduler {

	private static final Logger log = LoggerFactory.getLogger(TokenScheduler.class);

	@Scheduled(fixedRate = 5000)
    public void removeToken() {
        log.info("The time is now {}", LocalDateTime.now().toString());
    }
}
