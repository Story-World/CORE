package com.storyworld.domain.sql;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.storyworld.enums.Status;

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

	private Status status;

	private Float avgRate;

	@OneToMany(mappedBy = "story", cascade = CascadeType.ALL)
	private Set<Comment> listOfComment = new HashSet<>();

	@ManyToOne(cascade = CascadeType.ALL)
	private User author;

	public Story() {
	}

	public Story(String name, String description, LocalDateTime date, Status status, User author) {
		this.name = name;
		this.description = description;
		this.date = date;
		this.status = status;
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Float getAvgRate() {
		return avgRate;
	}

	public void setAvgRate(Float avgRate) {
		this.avgRate = avgRate;
	}

	public Set<Comment> getListOfComment() {
		return listOfComment;
	}

	public void setListOfComment(Set<Comment> listOfComment) {
		this.listOfComment = listOfComment;
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
				+ status + ", avgRate=" + avgRate + ", listOfComment=" + listOfComment + ", author=" + author + "]";
	}

}
