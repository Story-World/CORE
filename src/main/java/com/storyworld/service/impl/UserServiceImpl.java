package com.storyworld.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storyworld.domain.json.Message;
import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;
import com.storyworld.domain.json.StatusMessage;
import com.storyworld.domain.sql.Mail;
import com.storyworld.domain.sql.RestartPassword;
import com.storyworld.domain.sql.Role;
import com.storyworld.domain.sql.User;
import com.storyworld.enums.Status;
import com.storyworld.repository.sql.MailReposiotory;
import com.storyworld.repository.sql.RestartPasswordRepository;
import com.storyworld.repository.sql.RoleRepository;
import com.storyworld.repository.sql.UserRepository;
import com.storyworld.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private RestartPasswordRepository restartPasswordRepository;

	@Autowired
	private MailReposiotory mailReposiotory;

	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

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
		if (userLogon != null && userLogon.getName().equals(request.getUser().getName())
				&& userLogon.getPassword().equals(request.getUser().getPassword()) && (!userLogon.isBlock()
						|| ChronoUnit.MINUTES.between(userLogon.getLastIncoorectLogin(), LocalDateTime.now()) >= 10)) {
			userLogon.setToken(UUID.randomUUID().toString());
			userLogon.setLastActionTime(LocalDateTime.now());
			userLogon.setBlock(false);
			userLogon.setLastIncoorectLogin(null);
			userRepository.save(userLogon);
			response.setSuccess(true);
			message.setStatus(StatusMessage.SUCCESS);
			message.setMessage("LOGIN");
			response.setUser(request.getUser());
			response.setMessage(message);
		} else {
			if (userLogon != null) {
				int incrementIncorrect = userLogon.getIncorrectLogin();
				incrementIncorrect++;
				userLogon.setIncorrectLogin(incrementIncorrect);
				if (userLogon.getIncorrectLogin() == 5) {
					userLogon.setBlock(true);
					userLogon.setLastIncoorectLogin(LocalDateTime.now());
				}
				userRepository.save(userLogon);
			}
			response.setSuccess(false);
			message.setStatus(StatusMessage.ERROR);
			message.setMessage("INCORRECT_DATA");
			response.setMessage(message);
		}
	}

	@Override
	public void register(Request request, Response response) {
		Message message = new Message();
		try {
			User user = request.getUser();
			User userRegister = userRepository.save(user);
			Role role = roleRepository.findOne((long) 1);
			Set<Role> roles = new HashSet<>();
			roles.add(role);
			userRegister.setRoles(roles);
			userRepository.save(userRegister);
			response.setSuccess(true);
			message.setStatus(StatusMessage.INFO);
			message.setMessage("REGISTER");
			response.setUser(userRegister);
			response.setMessage(message);
			Mail mail = new Mail();
			mail.setStatus(Status.READY);
			mail.setTemplate("REGISTER");
			mail.setEmail(userRegister.getEmail());
			mailReposiotory.save(mail);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			response.setSuccess(false);
			message.setStatus(StatusMessage.ERROR);
			message.setMessage("INCORRECT_DATA");
			response.setMessage(message);
		}
	}

	@Override
	public void restartPassword(String email, Response response) {
		User user = userRepository.findByEmail(email);
		Message message = new Message();
		if (user != null) {
			response.setSuccess(true);
			message.setStatus(StatusMessage.INFO);
			message.setMessage("RESTARTED");
			response.setMessage(message);
			Mail mail = new Mail();
			mail.setStatus(Status.READY);
			mail.setTemplate("RESTART");
			mail.setEmail(user.getEmail());
			mailReposiotory.save(mail);
		} else {
			response.setSuccess(false);
			message.setStatus(StatusMessage.ERROR);
			message.setMessage("INCORRECT_DATA");
			response.setMessage(message);
		}
	}

	@Override
	public void confirmPassword(Request request, Response response) {
		RestartPassword restartPassword = restartPasswordRepository
				.findByToken(request.getRestartPassword().getToken());
		Message message = new Message();
		if (ChronoUnit.DAYS.between(restartPassword.getValidationTime(), LocalDateTime.now()) >= 1
				&& restartPassword.getToken().equals(request.getToken())) {
			User user = userRepository.findByRestartPassword(restartPassword);
			try {
				user.setPassword(request.getUser().getPassword());
				userRepository.save(user);
				response.setSuccess(true);
				message.setStatus(StatusMessage.INFO);
				message.setMessage("RESTARTED");
				response.setMessage(message);
			} catch (Exception e) {
				LOG.error(e.getMessage());
				message.setStatus(StatusMessage.ERROR);
				message.setMessage("INCORRECT_DATA");
				response.setMessage(message);
			}
		} else {
			message.setStatus(StatusMessage.ERROR);
			message.setMessage("INCORRECT_DATA");
			response.setMessage(message);
		}
	}

}