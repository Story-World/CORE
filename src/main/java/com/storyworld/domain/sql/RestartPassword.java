package com.storyworld.domain.sql;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RESTARTPASSWORD")
public class RestartPassword implements Serializable {

	private static final long serialVersionUID = 1475611475595422357L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String token;

	private LocalDateTime validationTime;

	public long getId() {
		return id;
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

}
