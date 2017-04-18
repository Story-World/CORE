package com.storyworld.domain.json;

import java.util.List;

import com.storyworld.domain.sql.User;
import com.storyworld.enums.TypeToken;

public class Request {

	private String token;

	private User user;

	private TypeToken tokenType;

	private List<FavouritePlaces> favouritePlaces;
	
	private int page;
	
	private int sizePage;

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

	public TypeToken getTokenType() {
		return tokenType;
	}

	public void setTokenType(TypeToken tokenType) {
		this.tokenType = tokenType;
	}

	public List<FavouritePlaces> getFavouritePlaces() {
		return favouritePlaces;
	}

	public void setFavouritePlaces(List<FavouritePlaces> favouritePlaces) {
		this.favouritePlaces = favouritePlaces;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSizePage() {
		return sizePage;
	}

	public void setSizePage(int sizePage) {
		this.sizePage = sizePage;
	}

	@Override
	public String toString() {
		return "Request [token=" + token + ", user=" + user + ", tokenType=" + tokenType + ", favouritePlaces="
				+ favouritePlaces + "]";
	}

}
