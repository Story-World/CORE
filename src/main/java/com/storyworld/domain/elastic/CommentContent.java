package com.storyworld.domain.elastic;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.data.elasticsearch.annotations.Document;

import com.storyworld.domain.sql.User;

@Document(indexName = "comment", type = "comment")
public class CommentContent {

	@Id
	private String id;

	@NotNull
	private String content;

	private User author;

	private int likes;

	private int dislikes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "CommentContent [id=" + id + ", content=" + content + ", author=" + author + ", likes=" + likes
				+ ", dislikes=" + dislikes + "]";
	}

}
