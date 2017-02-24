package com.packt.storyworld.domain.json;

import com.packt.storyworld.domain.sql.User;

public class JsonUser {

	private int id;

	private String name;

	private String password;

	private String email;

	public JsonUser(User user){
		this.id = user.getId();
		this.name = user.getName();
		this.password = user.getPassword();
		this.email = user.getEmail();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	
	
}
