package com.storyworld.domain.sql;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.storyworld.enums.StoryState;
import com.storyworld.enums.StoryType;

@Entity
@Table(name = "STORY")
public class Story implements Serializable {

	private static final long serialVersionUID = -3972670387724832603L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	@Column(unique = true)
	@Length(min = 4, max = 255)
	private String title;

	@NotNull
	@Length(min = 4, max = 255)
	private String description;

	private LocalDateTime date;

	@Enumerated(EnumType.STRING)
	@NotNull
	private StoryState state;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private StoryType type;

	private Float avgRate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "authorId")
	@NotNull
	private User author;

	public Story() {
	}

	public Story(String title, String description, LocalDateTime date, StoryState state, StoryType type, User author) {
		this.title = title;
		this.description = description;
		this.date = date;
		this.state = state;
		this.type = type;
		this.author = author;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return title;
	}

	public void setName(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public StoryState getState() {
		return state;
	}

	public void setState(StoryState state) {
		this.state = state;
	}
	
	public StoryType getType() {
		return type;
	}

	public void setType(StoryType type) {
		this.type = type;
	}

	public Float getAvgRate() {
		return avgRate;
	}

	public void setAvgRate(Float avgRate) {
		this.avgRate = avgRate;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "Story [id=" + id + ", title=" + title + ", description=" + description + ", date=" + date + ", state="
				+ state + ", avgRate=" + avgRate + ", author=" + author + "]";
	}

}
