package com.storyworld.service;

import com.storyworld.domain.json.Request;
import com.storyworld.domain.sql.User;

public interface AuthorizationService {

	public User checkAccessToUser(String token);

	public boolean checkAccess(Request request);

	public boolean checkAccessToUser(Request request);

	public boolean checkAccessToComment(Request request);
}
