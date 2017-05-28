package com.storyworld.service;

import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;
import com.storyworld.domain.sql.User;

public interface UserService {

	public void removeToken(User user);

	public void login(Request request, Response response);

	public void register(Request request, Response response);

	public void restartPassword(Request request, Response response);

	public void remindPassword(Request request, Response response);

	public void confirmRegister(Request request, Response response);

	public void changePassword(Request request, Response response);

	public void update(Request request, Response response);

	public void getUser(Request request, Response response);

	public void logout(Request request, Response response);

	public void getUsers(Request request, Response response);

	public void delete(Long id, Response response);

	public void get(Long id, Response response);

	public void block(Request request, Response response);

}
