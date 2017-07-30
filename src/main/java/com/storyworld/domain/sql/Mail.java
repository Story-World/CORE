package com.storyworld.domain.sql;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.storyworld.domain.sql.enums.Status;
import com.storyworld.domain.sql.enums.TypeToken;

import lombok.Data;

@Data
@Entity
@Table(name = "MAIL")
public class Mail implements Serializable {

	private static final long serialVersionUID = 4699548647329534908L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Enumerated(EnumType.STRING)
	private TypeToken template;

	@Enumerated(EnumType.STRING)
	private Status status;

	private LocalDateTime sent;

	@ManyToOne(fetch = FetchType.EAGER)
	private User user;

	public Mail() {
	}

	public Mail(TypeToken template, Status status, User user) {
		super();
		this.template = template;
		this.status = status;
		this.user = user;
	}

}
