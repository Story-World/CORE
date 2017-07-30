package com.storyworld.domain.sql;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.storyworld.domain.sql.enums.StoryType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "STORY")
public class Story implements Serializable {

	private static final long serialVersionUID = -3972670387724832603L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String contentId;

	@NotNull
	@Length(min = 4, max = 255)
	private String title;

	@NotNull
	@Length(min = 4, max = 255)
	private String description;

	private LocalDateTime date;

	@Enumerated(EnumType.STRING)
	@NotNull
	private StoryType type;

	private Float avgRate;

	@Transient
	private String rawText;

	@Transient
	private List<String> pages;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "authorId")
	@NotNull
	private User author;

	public Story() {
	}

	public Story(String title, String description, LocalDateTime date, StoryType type, User author) {
		this.title = title;
		this.description = description;
		this.date = date;
		this.type = type;
		this.author = author;
	}
}
