package com.storyworld.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.storyworld.domain.sql.Mail;
import com.storyworld.enums.Status;
import com.storyworld.repository.sql.MailReposiotory;
import com.storyworld.service.MailService;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private MailSender mailSender;

	@Autowired
	private MailReposiotory mailReposiotory;

	private SimpleMailMessage crunchifyMsg;

	private final String FROM = "mycookbook@gmail.com";

	private static final Logger LOG = LoggerFactory.getLogger(MailServiceImpl.class);

	@Override
	public void registration(String toAddress, String name) {
		crunchifyMsg = new SimpleMailMessage();
		crunchifyMsg.setFrom("mycookbook@gmail.com");
		crunchifyMsg.setTo(toAddress);
		crunchifyMsg.setSubject("Welcome in Cook Book");
		crunchifyMsg.setText("Hello " + name);
		mailSender.send(crunchifyMsg);
	}

	@Override
	public void sendEmail(Mail mail) {
		crunchifyMsg = new SimpleMailMessage();
		crunchifyMsg.setFrom(FROM);
		crunchifyMsg.setTo(mail.getEmail());
		crunchifyMsg.setSubject(mail.getTemplate());
		crunchifyMsg.setText("test");
		try {
			mailSender.send(crunchifyMsg);
			mail.setStatus(Status.FINISHED);
		} catch (MailException e) {
			mail.setStatus(Status.ERROR);
			LOG.error(e.getMessage());
		} finally {
			mailReposiotory.save(mail);
		}
	}

}
