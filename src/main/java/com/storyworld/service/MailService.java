package com.storyworld.service;

import com.storyworld.domain.sql.Mail;

public interface MailService {

	public void registration(String toAddress, String name);

	public void sendEmail(Mail mail);
}
