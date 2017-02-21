package com.packt.storyworld.repository;

import java.util.List;

import com.packt.storyworld.domain.sql.User;

public interface UserRepository {

	public List<User> getLogonUsers();

	public void update(User user);

	public User getUserByName(String name);

	public User register(User user);

}
