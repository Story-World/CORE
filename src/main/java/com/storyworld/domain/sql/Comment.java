package com.storyworld.domain.sql;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "COMMENT")
public class Comment implements Serializable {

	private static final long serialVersionUID = 2919807702926798174L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String _id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "authorId")
	@NotNull
	private User author;

	@ManyToOne
	@JoinColumn(name = "storyId")
	@NotNull
	private Story story;

	public Comment() {
	}

	public Comment(User author, Story story) {
		this.author = author;
		this.story = story;
	}

	public Comment(String _id) {
		this._id = _id;
	}

}
