package com.packt.storyworld.repository;

import java.util.List;

import com.packt.storyworld.domain.User;

public interface UserRepository {

	public List<User> getLogonUsers();

	public void update(User user);

}
