package com.storyworld.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

import com.storyworld.domain.json.Message;

@RestController
public class ChatController {

	@Autowired
	private SimpMessagingTemplate messaging;

	@MessageMapping("/chat")
	@SendToUser("/topic/chat")
	public void handleChat(Message message) {
		// System.out.println(message.getMessage());
		messaging.convertAndSend("/topic/chat", message);
		// return new Message(StatusMessage.INFO, message.getMessage());
	}
}
