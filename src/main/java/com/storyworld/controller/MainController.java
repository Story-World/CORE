package com.storyworld.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.storyworld.domain.elastic.CommentContent;
import com.storyworld.domain.sql.User;
import com.storyworld.repository.elastic.CommentContentRepository;
import com.storyworld.repository.elastic.StoryContentRepository;
import com.storyworld.repository.sql.UserRepository;

@Controller
@RestController
public class MainController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StoryContentRepository storyContentRepository;

	@Autowired
	private CommentContentRepository commentContentRepository;

	@Autowired
	private JavaMailSender javaMailSender;

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@RequestMapping
	public @ResponseBody CommentContent home() {
		User user = null;
		user.getName().split("");
		// return
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo("animalmatek@o2.pl");
		message.setSubject("123");
		message.setText("34324");
		javaMailSender.send(message);
		System.out.println(userRepository.findOne(1L));
		return commentContentRepository.findOne("AVvcY5X-9dLUkrWjYLKi");
	}

}