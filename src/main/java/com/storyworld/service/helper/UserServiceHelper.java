package com.storyworld.service.helper;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.storyworld.conditions.UserPredicate;
import com.storyworld.domain.json.Message;
import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;
import com.storyworld.domain.json.enums.StatusMessage;
import com.storyworld.domain.sql.Mail;
import com.storyworld.domain.sql.MailToken;
import com.storyworld.domain.sql.Role;
import com.storyworld.domain.sql.User;
import com.storyworld.domain.sql.enums.Status;
import com.storyworld.domain.sql.enums.TypeToken;
import com.storyworld.functionalInterface.JSONPrepare;
import com.storyworld.repository.sql.MailReposiotory;
import com.storyworld.repository.sql.MailTokenRepository;
import com.storyworld.repository.sql.RoleRepository;
import com.storyworld.repository.sql.UserRepository;

@Component
public class UserServiceHelper {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private MailTokenRepository mailTokenRepository;

	@Autowired
	private MailReposiotory mailReposiotory;

	@Autowired
	private UserPredicate userPredicate;

	private JSONPrepare<User> jsonPrepare = (statusMessage, message, user, list, success,
			counter) -> new Response<User>(new Message(statusMessage, message), user, list, success, counter);

	public Response<User> successLogin(User user) {
		user.setToken(UUID.randomUUID().toString());
		user.setLastActionTime(LocalDateTime.now());
		user.setBlock(false);
		user.setIncorrectLogin(0);
		user.setLastIncorrectLogin(null);
		userRepository.save(user);
		return jsonPrepare.prepareResponse(StatusMessage.SUCCESS, "LOGIN", user, null, true, null);
	}

	public Response<User> unsuccessLogin(User user) {
		int incrementIncorrect = user.getIncorrectLogin();
		incrementIncorrect++;
		user.setIncorrectLogin(incrementIncorrect);
		if (user.getIncorrectLogin() == 5) {
			user.setBlock(true);
			user.setLastIncorrectLogin(LocalDateTime.now());
		}
		userRepository.save(user);
		return jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false, null);
	}

	public Response<User> getUsersFromDB(int page, int sizePage) {
		return Optional.ofNullable(userRepository.findAll(new PageRequest(page, sizePage)))
				.map(users -> jsonPrepare.prepareResponse(null, null, null, users.getContent(), true, null))
				.orElseGet(() -> jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false,
						null));
	}

	public Response<User> confirmRegisterInDB(MailToken mailToken) {
		return Optional.ofNullable(userRepository.findOne(mailToken.getUser().getId())).map(user -> {
			user.setBlock(false);
			userRepository.save(user);
			mailTokenRepository.delete(mailToken);
			return jsonPrepare.prepareResponse(StatusMessage.SUCCESS, "SUCCESS_REGISTERED", null, null, true, null);
		}).orElseGet(() -> jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false, null));
	}

	public Response<User> updateUserNameOrMail(User userRequest, User user) {
		Optional.ofNullable(user.getName()).ifPresent(user::setName);
		Optional.ofNullable(user.getMail()).ifPresent(user::setMail);
		user.setLastActionTime(LocalDateTime.now());
		userRepository.save(user);
		return jsonPrepare.prepareResponse(StatusMessage.SUCCESS, "SUCCESS_UPDATED", user, null, true, null);
	}

	public Response<User> updateUserPassword(String newPassword, User user, MailToken mailToken) {
		user.setPassword(newPassword);
		userRepository.save(user);
		Optional.ofNullable(mailToken).ifPresent(token -> mailTokenRepository.delete(mailToken));
		return jsonPrepare.prepareResponse(StatusMessage.SUCCESS, "PASSWORD_CHANGED", user, null, true, null);
	}

	public Response<User> changePasswordIfMailTokenIsValid(Request request, MailToken mailToken) {
		return userPredicate.validTokenWithTime.test(mailToken, request)
				? Optional.ofNullable(userRepository.findOne(mailToken.getUser().getId()))
						.map(user -> updateUserPassword(request.getUser().getPassword(), user, mailToken))
						.orElseGet(() -> jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null,
								false, null))
				: jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false, null);
	}

	public Response<User> prepareUserToSave(User user) {
		user.setBlock(false);
		user.setDeleted(false);
		User userRegister = userRepository.save(user);
		Role role = roleRepository.findOne((long) 1);
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		userRegister.setRoles(roles);
		userRepository.save(userRegister);
		createMailToken(TypeToken.REGISTER, userRegister);
		Mail mail = new Mail(TypeToken.REGISTER, Status.READY, userRegister);
		mailReposiotory.save(mail);
		return jsonPrepare.prepareResponse(StatusMessage.SUCCESS, "REGISTER", null, null, true, null);
	}

	public void createMailToken(TypeToken typeToken, User user) {
		MailToken mailToken = new MailToken(typeToken, UUID.randomUUID().toString(), LocalDateTime.now(), user);
		if (typeToken.equals(TypeToken.RESTART_PASSWORD))
			mailTokenRepository.save(mailTokenRepository.findByUser(user).map(tokens -> {
				tokens.add(mailToken);
				return tokens;
			}).get());
		else {
			Set<MailToken> tokens = new HashSet<>();
			tokens.add(mailToken);
			mailTokenRepository.save(tokens);
		}
	}

	public Response<User> addMailToMailerAfterRestartPassword(User user) {
		return mailTokenRepository.findByUserAndTypeToken(user, TypeToken.RESTART_PASSWORD)
				.map(mailToken -> saveMailTokenAfterRequestForRestartPassword(mailToken, user))
				.orElseGet(() -> jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false,
						null));
	}

	public Response<User> saveMailTokenAfterRequestForRestartPassword(MailToken mailToken, User user) {
		long id = mailToken.getId();
		mailTokenRepository.findByUser(user).map(tokens -> {
			tokens.removeIf(x -> x.getId() == id);
			mailToken.setValidationTime(LocalDateTime.now());
			mailToken.setToken(UUID.randomUUID().toString());
			tokens.add(mailToken);
			mailTokenRepository.save(tokens);
			return jsonPrepare.prepareResponse(StatusMessage.INFO, "RESTARTED", null, null, true, null);
		}).orElseGet(() -> {
			createMailToken(TypeToken.RESTART_PASSWORD, user);
			return jsonPrepare.prepareResponse(StatusMessage.INFO, "RESTARTED", null, null, true, null);
		});
		Mail mail = new Mail(TypeToken.RESTART_PASSWORD, Status.READY, user);
		mailReposiotory.save(mail);
		return jsonPrepare.prepareResponse(StatusMessage.INFO, "RESTARTED", null, null, true, null);
	}

}
