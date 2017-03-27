package com.storyworld.domain.json;

import java.util.List;

import com.storyworld.domain.sql.User;
import com.storyworld.enums.TypeTokenStatus;

public class Request {

	private String token;

	private User user;

	private TypeTokenStatus tokenType;

	private List<FavouritePlaces> favouritePlaces;

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

	public TypeTokenStatus getTokenType() {
		return tokenType;
	}

	public void setTokenType(TypeTokenStatus tokenType) {
		this.tokenType = tokenType;
	}

	public List<FavouritePlaces> getFavouritePlaces() {
		return favouritePlaces;
	}

	public void setFavouritePlaces(List<FavouritePlaces> favouritePlaces) {
		this.favouritePlaces = favouritePlaces;
	}

	@Override
	public String toString() {
		return "Request [token=" + token + ", user=" + user + ", tokenType=" + tokenType + ", favouritePlaces="
				+ favouritePlaces + "]";
	}

}
