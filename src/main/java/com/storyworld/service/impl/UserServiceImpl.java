package com.storyworld.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storyworld.domain.json.Message;
import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;
import com.storyworld.domain.json.StatusMessage;
import com.storyworld.domain.sql.Mail;
import com.storyworld.domain.sql.User;
import com.storyworld.enums.Status;
import com.storyworld.repository.sql.MailReposiotory;
import com.storyworld.repository.sql.UserRepository;
import com.storyworld.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MailReposiotory mailReposiotory;

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
		User user = request.getUser();
		if (user.getEmail() != null && user.getName() != null && user.getPassword() != null) {
			/*Role role = new Role();
			role.setName("USER");
			Set<Role> roles = new HashSet<>();
			roles.add(role);
			user.setRoles(roles);*/
			User userRegister = userRepository.save(user);
			Message message = new Message();
			if (userRegister != null) {
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
			} else {
				response.setSuccess(false);
				message.setStatus(StatusMessage.ERROR);
				message.setMessage("INCORRECT_DATA");
				response.setMessage(message);
			}
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

	}

}