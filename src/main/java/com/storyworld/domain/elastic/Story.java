package com.storyworld.domain.elastic;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.elasticsearch.annotations.Document;

import com.storyworld.domain.sql.User;
import com.storyworld.enums.StoryState;
import com.storyworld.enums.StoryType;

@Document(indexName = "story", type = "story")
public class Story {

	private long id;

	private String name;

	private String description;

	private List<String> text;

	private LocalDateTime date;

	private StoryState status;

	private StoryType type;

	private User author;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getText() {
		return text;
	}

	public void setText(List<String> text) {
		this.text = text;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public StoryState getStatus() {
		return status;
	}

	public void setStatus(StoryState status) {
		this.status = status;
	}

	public StoryType getType() {
		return type;
	}

	public void setType(StoryType type) {
		this.type = type;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public long getId() {
		return id;
	}

}
