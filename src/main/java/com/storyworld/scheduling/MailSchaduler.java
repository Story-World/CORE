package com.storyworld.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.storyworld.enums.Status;
import com.storyworld.repository.sql.MailReposiotory;
import com.storyworld.service.MailService;

public class MailSchaduler {

	@Autowired
	private MailReposiotory mailRepository;

	@Autowired
	private MailService mailService;

	@Scheduled(fixedRate = 60000)
	public void sendMail() {
		mailRepository.findByStatus(Status.READY).forEach(mailService::sendEmail);
	}

}
