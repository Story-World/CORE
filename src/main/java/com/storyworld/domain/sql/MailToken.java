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

import com.storyworld.enums.TypeToken;

@Entity
@Table(name = "MAIL_TOKEN")
public class MailToken implements Serializable {

	private static final long serialVersionUID = 1475611475595422357L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Enumerated(EnumType.STRING)
	private TypeToken typeToken;

	private String token;

	private LocalDateTime validationTime;

	@ManyToOne(fetch = FetchType.EAGER)
	private User user;

	public MailToken() {
		super();
	}

	public MailToken(TypeToken typeToken, String token, LocalDateTime validationTime, User user) {
		super();
		this.typeToken = typeToken;
		this.token = token;
		this.validationTime = validationTime;
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public TypeToken getTypeToken() {
		return typeToken;
	}

	public void setTypeToken(TypeToken typeToken) {
		this.typeToken = typeToken;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getValidationTime() {
		return validationTime;
	}

	public void setValidationTime(LocalDateTime validationTime) {
		this.validationTime = validationTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
