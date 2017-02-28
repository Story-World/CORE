package com.packt.storyworld.domain.json;

import com.packt.storyworld.domain.sql.User;

public class Request {

	private String token;

	private User user;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
