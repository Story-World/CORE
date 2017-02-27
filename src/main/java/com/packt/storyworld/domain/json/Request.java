package com.packt.storyworld.domain.json;

import com.packt.storyworld.domain.sql.User;

public class Request {

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
