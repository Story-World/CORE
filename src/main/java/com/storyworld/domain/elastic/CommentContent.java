package com.storyworld.domain.elastic;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;

import com.storyworld.domain.sql.User;

@Document(indexName = "comment", type = "comment")
public class CommentContent {

	@Id
	private String id;

	private String content;

	private User author;

	private int likes;

	private int dislikes;

	private boolean edited;

	private String date;

	private Long storyId;

	public String getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getDislikes() {
		return dislikes;
	}

	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}

	public boolean isEdited() {
		return edited;
	}

	public void setEdited(boolean edited) {
		this.edited = edited;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Long getStoryId() {
		return storyId;
	}

	public void setStoryId(Long storyId) {
		this.storyId = storyId;
	}

}
