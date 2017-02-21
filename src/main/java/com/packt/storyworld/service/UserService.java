package com.packt.storyworld.service;

import com.packt.storyworld.domain.json.Response;
import com.packt.storyworld.domain.sql.User;

public interface UserService {

	public void removeToken(User user);

	public void login(User user, Response response);

	public void register(User user, Response response);
}
