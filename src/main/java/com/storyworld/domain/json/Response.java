package com.storyworld.domain.json;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.storyworld.domain.elastic.CommentContent;
import com.storyworld.domain.sql.Story;
import com.storyworld.domain.sql.User;

@SuppressWarnings("deprecation")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Response {

	private boolean success;

	private Message message;

	private User user;

	private Page<User> users;

	private List<FavouritePlaces> favouritePlaces;

	private List<CommentContent> comments;

	private CommentContent comment;
	
	private Story story;
	
	private List<Story> stories;

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

	public List<CommentContent> getComments() {
		return comments;
	}

	public void setComments(List<CommentContent> comments) {
		this.comments = comments;
	}

	public CommentContent getComment() {
		return comment;
	}

	public void setComment(CommentContent comment) {
		this.comment = comment;
	}
	
	public Story getStory() {
		return story;
	}

	public void setStory(Story story) {
		this.story = story;
	}

	public List<Story> getStories() {
		return stories;
	}

	public void setStories(List<Story> stories) {
		this.stories = stories;
	}

	@Override
	public String toString() {
		return "Response [success=" + success + ", message=" + message + ", user=" + user + ", users=" + users
				+ ", favouritePlaces=" + favouritePlaces + ", comments=" + comments + ", comment=" + comment + "]";
	}

}
