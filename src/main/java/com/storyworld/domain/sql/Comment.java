package com.storyworld.domain.sql;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "COMMENT")
public class Comment implements Serializable {

	private static final long serialVersionUID = 2919807702926798174L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(cascade = CascadeType.ALL)
	private User author;

	@ManyToOne(cascade = CascadeType.ALL)
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
