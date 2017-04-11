package com.storyworld.domain.json;

public class Message {

	private StatusMessage status;

	private String message;

	public Message() {
	}

	public Message(StatusMessage status, String message) {
		this.status = status;
		this.message = message;
	}

	public StatusMessage getStatus() {
		return status;
	}

	public void setStatus(StatusMessage status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
