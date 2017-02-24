package com.packt.storyworld.domain.json;

public class Response {

	private boolean success;

	private Message message;
	
	private JsonUser user;

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

	public JsonUser getUser() {
		return user;
	}

	public void setUser(JsonUser user) {
		this.user = user;
	}

}
