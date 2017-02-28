package com.packt.storyworld.service;

import com.packt.storyworld.domain.json.Request;
import com.packt.storyworld.domain.json.Response;
import com.packt.storyworld.domain.sql.User;

public interface UserService {

	public void removeToken(User user);

	public void login(Request request, Response response);

	public void register(Request request, Response response);
}
