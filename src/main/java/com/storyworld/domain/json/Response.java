package com.storyworld.domain.json;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.storyworld.domain.elastic.CommentContent;
import com.storyworld.domain.sql.User;

@SuppressWarnings("deprecation")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Response {

	private boolean success;

	private Message message;

	private User user;

	private Page<User> users;

	private List<FavouritePlaces> favouritePlaces;

	private Set<CommentContent> comments;

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

	public Page<User> getUsers() {
		return users;
	}

	public void setUsers(Page<User> users) {
		this.users = users;
	}

	public List<FavouritePlaces> getFavouritePlaces() {
		return favouritePlaces;
	}

	public void setFavouritePlaces(List<FavouritePlaces> favouritePlaces) {
		this.favouritePlaces = favouritePlaces;
	}

	public Set<CommentContent> getComments() {
		return comments;
	}

	public void setComments(Set<CommentContent> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Response [success=" + success + ", message=" + message + ", user=" + user + ", favouritePlaces="
				+ favouritePlaces + "]";
	}

}
