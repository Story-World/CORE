package com.storyworld.domain.sql;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

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

	@NotNull
	@Column(unique = true)
	@Length(min = 1, max = 255)
	private String name;

	@NotNull
	@Length(min = 1, max = 255)
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	@Transient
	private String confirmPassword;

	@NotNull
	@Column(unique = true)
	@Length(min = 1, max = 255)
	@Pattern(regexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}")
	private String mail;

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

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Role> roles = new HashSet<>();

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	private RestartPassword restartPassword;

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

	public String getConfirmPassword() {
		return confirmPassword;
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

	public LocalDateTime getLastIncoorectLogin() {
		return lastIncoorectLogin;
	}

	public void setLastIncoorectLogin(LocalDateTime lastIncoorectLogin) {
		this.lastIncoorectLogin = lastIncoorectLogin;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public RestartPassword getRestartPassword() {
		return restartPassword;
	}

	public void setRestartPassword(RestartPassword restartPassword) {
		this.restartPassword = restartPassword;
	}

}
