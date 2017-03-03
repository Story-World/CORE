package com.storyworld.domain.json;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.storyworld.domain.sql.User;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Response {

	private boolean success;

	private Message message;
	
	private User user;
	
	private List<FavouritePlaces> favouritePlaces;

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

	public List<FavouritePlaces> getFavouritePlaces() {
		return favouritePlaces;
	}

	public void setFavouritePlaces(List<FavouritePlaces> favouritePlaces) {
		this.favouritePlaces = favouritePlaces;
	}
	
	

}
