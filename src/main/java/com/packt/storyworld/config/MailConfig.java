package com.packt.storyworld.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

	private static final String PROPERTY_NAME_HOST = "smtp.gmail.com";
	private static final int PROPERTY_NAME_PORT = 587;
	private static final String PROPERTY_NAME_USERNAME = "mycookbook.sup";
	private static final String PROPERTY_NAME_PASSWORD = "myCookBook2016";
	private static final String PROPERTY_NAME_PROTOCOL = "smtp";
	private static final String PROPERTY_NAME_AUTH = "true";
	private static final String PROPERTY_NAME_STARTTLS_ENABLE = "true";
	private static final String PROPERTY_NAME_DEBUG = "true";

	@Bean
	public JavaMailSender javaMailService() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

		javaMailSender.setHost(PROPERTY_NAME_HOST);
		javaMailSender.setPort(PROPERTY_NAME_PORT);
		javaMailSender.setUsername(PROPERTY_NAME_USERNAME);
		javaMailSender.setPassword(PROPERTY_NAME_PASSWORD);

		javaMailSender.setJavaMailProperties(getMailProperties());

		return javaMailSender;
	}

	private Properties getMailProperties() {
		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol", PROPERTY_NAME_PROTOCOL);
		properties.setProperty("mail.smtp.auth", PROPERTY_NAME_AUTH);
		properties.setProperty("mail.smtp.starttls.enable", PROPERTY_NAME_STARTTLS_ENABLE);
		properties.setProperty("mail.debug", PROPERTY_NAME_DEBUG);
		return properties;
	}
}
