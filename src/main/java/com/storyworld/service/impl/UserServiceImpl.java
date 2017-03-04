package com.storyworld.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storyworld.domain.json.Message;
import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;
import com.storyworld.domain.json.StatusMessage;
import com.storyworld.domain.sql.User;
import com.storyworld.repository.sql.UserRepository;
import com.storyworld.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void removeToken(User user) {
		if (ChronoUnit.HOURS.between(user.getLastActionTime(), LocalDateTime.now()) >= 2) {
			user.setLastActionTime(null);
			user.setToken(null);
			userRepository.save(user);
		}
	}

	@Override
	public void login(Request request, Response response) {
		User userLogon = userRepository.findByName(request.getUser().getName());
		Message message = new Message();
<<<<<<< HEAD:src/main/java/com/packt/storyworld/service/impl/UserServiceImpl.java
		if (userLogon != null && userLogon.getName().equals(request.getUser().getName())
				&& userLogon.getPassword().equals(request.getUser().getPassword())) {
			response.setSuccess(true);
			message.setStatusMessage(StatusMessage.INFO);
			message.setMessage("LOGIN");
			response.setUser(userLogon);
			response.setMessage(message);
=======
		if (userLogon != null) {
			if (userLogon.getName().equals(request.getUser().getName())
					&& userLogon.getPassword().equals(request.getUser().getPassword())) {
				response.setSuccess(true);
				message.setStatus(StatusMessage.SUCCESS);
				message.setMessage("LOGIN");
				response.setUser(request.getUser());
				response.setMessage(message);
			}
>>>>>>> 0f1b820abcd7f476f4f56d35a01eee31c02e1926:src/main/java/com/storyworld/service/impl/UserServiceImpl.java
		} else {
			response.setSuccess(false);
			message.setStatus(StatusMessage.ERROR);
			message.setMessage("INCORRECT_DATA");
			response.setMessage(message);
		}
	}

	@Override
<<<<<<< HEAD:src/main/java/com/packt/storyworld/service/impl/UserServiceImpl.java
	public void register(Request request, Response response) {
		if (request.getUser() != null) {
			User userRegister = userRepository.save(request.getUser());
			Message message = new Message();
			if (userRegister != null) {
				response.setSuccess(true);
				message.setStatusMessage(StatusMessage.INFO);
				message.setMessage("REGISTER");
				response.setMessage(message);
			} else {
				response.setSuccess(false);
				message.setStatusMessage(StatusMessage.ERROR);
				message.setMessage("INCORRECT_DATA");
				response.setMessage(message);
			}
=======
	public void register(User user, Response response) {
		User userRegister = userRepository.save(user);
		Message message = new Message();
		if (userRegister != null) {
			response.setSuccess(true);
			message.setStatus(StatusMessage.INFO);
			message.setMessage("REGISTER");
			response.setUser(user);
			response.setMessage(message);
		} else {
			response.setSuccess(false);
			message.setStatus(StatusMessage.ERROR);
			message.setMessage("INCORRECT_DATA");
			response.setMessage(message);
>>>>>>> 0f1b820abcd7f476f4f56d35a01eee31c02e1926:src/main/java/com/storyworld/service/impl/UserServiceImpl.java
		}
	}

}
