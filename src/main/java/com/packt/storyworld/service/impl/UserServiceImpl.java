package com.packt.storyworld.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packt.storyworld.domain.json.Message;
import com.packt.storyworld.domain.json.Response;
import com.packt.storyworld.domain.json.StatusMessage;
import com.packt.storyworld.domain.sql.User;
import com.packt.storyworld.repository.UserRepository;
import com.packt.storyworld.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void removeToken(User user) {
		if (ChronoUnit.HOURS.between(user.getTime(), LocalDateTime.now()) >= 2) {
			user.setTime(null);
			user.setToken(null);
			userRepository.update(user);
		}
	}

	@Override
	public void login(User user, Response response) {
		User userLogon = userRepository.getUserByName(user.getName());
		Message message = new Message();
		if (userLogon != null && userLogon.getName().equals(user.getName())
				&& userLogon.getPassword().equals(user.getPassword())) {
			response.setSuccess(true);
			message.setStatusMessage(StatusMessage.INFO);
			message.setMessage("LOGIN");
			response.setObject(user);
			response.setMessage(message);
		} else {
			response.setSuccess(false);
			message.setStatusMessage(StatusMessage.ERROR);
			message.setMessage("INCORRECT_DATA");
			response.setMessage(message);
		}
	}

	@Override
	public void register(User user, Response response) {
		User userRegister = userRepository.register(user);
		Message message = new Message();
		if (userRegister != null) {
			response.setSuccess(true);
			message.setStatusMessage(StatusMessage.INFO);
			message.setMessage("REGISTER");
			response.setObject(userRegister);
			response.setMessage(message);
		} else {
			response.setSuccess(false);
			message.setStatusMessage(StatusMessage.ERROR);
			message.setMessage("INCORRECT_DATA");
			response.setMessage(message);
		}
	}

}
