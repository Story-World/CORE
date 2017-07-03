package com.storyworld.domain.sql;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 4929826277648185458L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	private String mail;

	private String token;

	private LocalDateTime lastActionTime;

	private int incorrectLogin;

	private boolean block;

	private LocalDateTime lastIncorrectLogin;

	private boolean deleted;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getLastActionTime() {
		return lastActionTime;
	}

	public void setLastActionTime(LocalDateTime lastActionTime) {
		this.lastActionTime = lastActionTime;
	}

	public int getIncorrectLogin() {
		return incorrectLogin;
	}

	public void setIncorrectLogin(int incorrectLogin) {
		this.incorrectLogin = incorrectLogin;
	}

	public boolean isBlock() {
		return block;
	}

	public void setBlock(boolean block) {
		this.block = block;
	}

	public LocalDateTime getLastIncorrectLogin() {
		return lastIncorrectLogin;
	}

	public void setLastIncorrectLogin(LocalDateTime lastIncorrectLogin) {
		this.lastIncorrectLogin = lastIncorrectLogin;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public long getId() {
		return id;
	}

}
