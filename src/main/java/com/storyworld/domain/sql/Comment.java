package com.storyworld.domain.sql;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "COMMENT")
public class Comment implements Serializable {

	private static final long serialVersionUID = 2919807702926798174L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "authorId")
	@NotNull
	private User author;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storyId")
	@NotNull
	private Story story;

	public Comment() {
	}

	public Comment(User author, Story story) {
		this.author = author;
		this.story = story;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Story getStory() {
		return story;
	}

	public void setStory(Story story) {
		this.story = story;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", author=" + author + ", story=" + story + "]";
	}

}
