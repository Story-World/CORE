package com.storyworld.domain.json;

import java.util.List;

import com.storyworld.domain.elastic.CommentContent;
import com.storyworld.domain.sql.Comment;
import com.storyworld.domain.sql.Story;
import com.storyworld.domain.sql.User;
import com.storyworld.enums.TypeToken;

public class Request {

	private String token;

	private User user;

	private TypeToken tokenType;

	private List<FavouritePlaces> favouritePlaces;

	private CommentContent commentContent;

	private Comment comment;

	private Story story;

	private int page;

	private int sizePage;

	public Request() {
	}

	public Request(String token) {
		this.token = token;
	}

	public Request(String token, Comment comment) {
		this.token = token;
		this.setComment(comment);
	}

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

	public CommentContent getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(CommentContent commentContent) {
		this.commentContent = commentContent;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public Story getStory() {
		return story;
	}

	public void setStory(Story story) {
		this.story = story;
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
				+ favouritePlaces + ", commentContent=" + commentContent + ", comment=" + comment + ", story=" + story
				+ ", page=" + page + ", sizePage=" + sizePage + "]";
	}

}
