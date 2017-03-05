package com.storyworld.domain.sql;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "USER")
public class User implements Serializable {

	private static final long serialVersionUID = -3325353040709283369L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	private String email;

	@JsonIgnore
	private String token;

	@JsonIgnore
	private LocalDateTime lastActionTime;

	@JsonIgnore
	private int incorrectLogin;

	@JsonIgnore
	private boolean block;

	@JsonIgnore
	private LocalDateTime lastIncoorectLogin;

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	/**
	 * @return the incorrectLogin
	 */
	public int getIncorrectLogin() {
		return incorrectLogin;
	}

	/**
	 * @param incorrectLogin
	 *            the incorrectLogin to set
	 */
	public void setIncorrectLogin(int incorrectLogin) {
		this.incorrectLogin = incorrectLogin;
	}

	/**
	 * @return the block
	 */
	public boolean isBlock() {
		return block;
	}

	/**
	 * @param block
	 *            the block to set
	 */
	public void setBlock(boolean block) {
		this.block = block;
	}

	/**
	 * @return the lastIncoorectLogin
	 */
	public LocalDateTime getLastIncoorectLogin() {
		return lastIncoorectLogin;
	}

	/**
	 * @param lastIncoorectLogin
	 *            the lastIncoorectLogin to set
	 */
	public void setLastIncoorectLogin(LocalDateTime lastIncoorectLogin) {
		this.lastIncoorectLogin = lastIncoorectLogin;
	}

}
