package com.packt.storyworld.domain.json;

import com.packt.storyworld.domain.sql.User;

public class Response {

	private boolean success;

	private Message message;
	
	private User user;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
