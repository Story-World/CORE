package com.packt.storyworld.domain.json;

public class Message {

	private StatusMessage status;

	private String message;

	public StatusMessage getStatusMessage() {
		return status;
	}

	public void setStatusMessage(StatusMessage status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
