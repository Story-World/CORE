package com.storyworld.domain.sql;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.storyworld.enums.StoryStatus;
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
	private String name;

	@NotNull
	@Length(min = 4, max = 255)
	private String description;

	private LocalDateTime date;

	@Enumerated(EnumType.STRING)
	@NotNull
	private StoryStatus status;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private StoryType type;

	private Float avgRate;

	@ManyToOne(cascade = CascadeType.ALL)
	private User author;

	public Story() {
	}

	public Story(String name, String description, LocalDateTime date, StoryStatus status, StoryType type, User author) {
		this.name = name;
		this.description = description;
		this.date = date;
		this.status = status;
		this.type = type;
		this.author = author;
	}

	public long getId() {
		return id;
	}

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

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public StoryStatus getStatus() {
		return status;
	}

	public void setStatus(StoryStatus status) {
		this.status = status;
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
		return "Story [id=" + id + ", name=" + name + ", description=" + description + ", date=" + date + ", status="
				+ status + ", avgRate=" + avgRate + ", author=" + author + "]";
	}

}
