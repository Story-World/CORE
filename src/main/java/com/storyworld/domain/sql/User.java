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
import javax.persistence.Table;
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
	@Length(min = 4, max = 255)
	private String name;

	@NotNull
	@Length(min = 6, max = 255)
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	@NotNull
	@Column(unique = true)
	@Length(min = 1, max = 255)
	@Pattern(regexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}")
	private String mail;

	@JsonProperty(access = Access.READ_ONLY)
	private String token;

	@JsonIgnore
	private LocalDateTime lastActionTime;

	@JsonIgnore
	private int incorrectLogin;

	@JsonIgnore
	private boolean block;

	@JsonIgnore
	private LocalDateTime lastIncorrectLogin;

	@JsonIgnore
	private boolean deleted;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Role> roles = new HashSet<>();

	public User() {
	}

	public User(String name, String password, String mail, String token, LocalDateTime lastActionTime,
			int incorrectLogin, boolean block, LocalDateTime lastIncorrectLogin, boolean deleted, Set<Role> roles) {
		this.name = name;
		this.password = password;
		this.mail = mail;
		this.token = token;
		this.lastActionTime = lastActionTime;
		this.incorrectLogin = incorrectLogin;
		this.block = block;
		this.lastIncorrectLogin = lastIncorrectLogin;
		this.deleted = deleted;
		this.roles = roles;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public void setDelete(boolean deleted) {
		this.deleted = deleted;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", mail=" + mail + ", token=" + token
				+ ", lastActionTime=" + lastActionTime + ", incorrectLogin=" + incorrectLogin + ", block=" + block
				+ ", lastIncorrectLogin=" + lastIncorrectLogin + ", deleted=" + deleted + ", roles=" + roles
				+ "]";
	}

}
