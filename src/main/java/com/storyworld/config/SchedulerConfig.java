package com.storyworld.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.storyworld.scheduling.MailSchaduler;
import com.storyworld.scheduling.TokenScheduler;

@Configuration
@EnableScheduling
public class SchedulerConfig {

	@Bean
	public TokenScheduler token() {
		return new TokenScheduler();
	}

	@Bean
	public MailSchaduler mail() {
		return new MailSchaduler();
	}
}
