package com.storyworld.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.storyworld.service.MailService;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private MailSender mailSender;

	private SimpleMailMessage crunchifyMsg;

	@Override
	public void registration(String toAddress, String name) {
		crunchifyMsg = new SimpleMailMessage();
		crunchifyMsg.setFrom("mycookbook@gmail.com");
		crunchifyMsg.setTo(toAddress);
		crunchifyMsg.setSubject("Welcome in Cook Book");
		crunchifyMsg.setText("Hello " + name);
		mailSender.send(crunchifyMsg);
	}

}
