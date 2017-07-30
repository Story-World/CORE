package com.storyworld.service;

import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;
import com.storyworld.domain.sql.User;

public interface UserService {

	public void removeToken(User user);

	public Response login(Request request);

	public Response register(Request request);

	public Response restartPassword(Request request);

	public Response remindPassword(Request request);

	public Response confirmRegister(Request request);

	public Response changePassword(Request request);

	public Response update(Request request);

	public Response getUser(Request requeste);

	public Response logout(Request request);

	public Response getUsers(Request request);

	public Response delete(Long id);

	public Response get(Long id);

	public Response block(Request request);

}
