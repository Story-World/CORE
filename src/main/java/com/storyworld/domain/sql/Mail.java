package com.storyworld.domain.sql;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.storyworld.enums.Status;

@Entity
@Table(name = "MAIL")
public class Mail implements Serializable {

	private static final long serialVersionUID = 4699548647329534908L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String template;

	@Enumerated(EnumType.STRING)
	private Status status;
	
	private LocalDateTime sent;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private User user;

	public long getId() {
		return id;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LocalDateTime getSent() {
		return sent;
	}

	public void setSent(LocalDateTime sent) {
		this.sent = sent;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
