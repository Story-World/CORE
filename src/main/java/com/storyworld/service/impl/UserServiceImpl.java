package com.storyworld.service.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.persistence.PersistenceException;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.storyworld.conditions.CommonPredicate;
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
import com.storyworld.service.UserService;

@Service
public class UserServiceImpl implements UserService {

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

	@Autowired
	private CommonPredicate commonPredicate;

	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	private JSONPrepare<User> jsonPrepare = (statusMessage, message, user, list,
			success) -> new Response<User>(new Message(statusMessage, message), user, list, success);

	@Override
	public void removeToken(User user) {
		if (userPredicate.checkValidTimeInMinutes.test(user.getLastActionTime(), 120)) {
			user.setLastActionTime(null);
			user.setToken(null);
			userRepository.save(user);
		}
	}

	@Override
	public Response<User> login(Request request) {
		return userRepository.findByName(request.getUser().getName()).map(user -> {
			if (userPredicate.vaildUserLogin.test(user, request)) {
				user.setToken(UUID.randomUUID().toString());
				user.setLastActionTime(LocalDateTime.now());
				user.setBlock(false);
				user.setIncorrectLogin(0);
				user.setLastIncorrectLogin(null);
				userRepository.save(user);
				return jsonPrepare.prepareResponse(StatusMessage.SUCCESS, "LOGIN", user, null, true);
			} else {
				int incrementIncorrect = user.getIncorrectLogin();
				incrementIncorrect++;
				user.setIncorrectLogin(incrementIncorrect);
				if (user.getIncorrectLogin() == 5) {
					user.setBlock(true);
					user.setLastIncorrectLogin(LocalDateTime.now());
				}
				userRepository.save(user);
				return jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false);
			}
		}).orElseGet(() -> jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
	}

	@Override
	public Response<User> register(Request request) {
		try {
			return Optional.ofNullable(request.getUser()).map(user -> {
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
				return jsonPrepare.prepareResponse(StatusMessage.SUCCESS, "REGISTER", null, null, true);
			}).orElseGet(() -> jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
		} catch (PersistenceException e) {
			LOG.error(e.toString());
			if (e.getCause() instanceof ConstraintViolationException)
				return jsonPrepare.prepareResponse(StatusMessage.ERROR, "UNIQUE_NAME_OR_EMAIL", null, null, false);
			else
				return jsonPrepare.prepareResponse(StatusMessage.ERROR, "UNIQUE_NAME_OR_EMAIL", null, null, false);
		} catch (Exception e) {
			LOG.error(e.toString());
			return jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false);
		}
	}

	private void createMailToken(TypeToken typeToken, User user) {
		MailToken mailToken = new MailToken(typeToken, UUID.randomUUID().toString(), LocalDateTime.now(), user);
		if (typeToken.equals(TypeToken.RESTART_PASSWORD)) {
			mailTokenRepository.save(mailTokenRepository.findByUser(user).map(tokens -> {
				tokens.add(mailToken);
				return tokens;
			}).get());
		} else {
			Set<MailToken> tokens = new HashSet<>();
			tokens.add(mailToken);
			mailTokenRepository.save(tokens);
		}
	}

	@Override
	public Response<User> restartPassword(Request request) {
		return userRepository.findByMail(request.getUser().getMail()).map(user -> {
			return mailTokenRepository.findByUserAndTypeToken(user, TypeToken.RESTART_PASSWORD).map(mailToken -> {
				long id = mailToken.getId();
				mailTokenRepository.findByUser(user).map(tokens -> {
					tokens.removeIf(x -> x.getId() == id);
					mailToken.setValidationTime(LocalDateTime.now());
					mailToken.setToken(UUID.randomUUID().toString());
					tokens.add(mailToken);
					mailTokenRepository.save(tokens);
					return jsonPrepare.prepareResponse(StatusMessage.INFO, "RESTARTED", null, null, true);
				}).orElseGet(() -> {
					createMailToken(TypeToken.RESTART_PASSWORD, user);
					return jsonPrepare.prepareResponse(StatusMessage.INFO, "RESTARTED", null, null, true);
				});
				Mail mail = new Mail(TypeToken.RESTART_PASSWORD, Status.READY, user);
				mailReposiotory.save(mail);
				return jsonPrepare.prepareResponse(StatusMessage.INFO, "RESTARTED", null, null, true);
			}).orElseGet(() -> jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
		}).orElseGet(() -> jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
	}

	@Override
	public Response<User> remindPassword(Request request) {
		return mailTokenRepository.findByToken(request.getToken()).map(mailToken -> {
			if (userPredicate.validTokenWithTime.test(mailToken, request)) {
				return Optional.ofNullable(userRepository.findOne(mailToken.getUser().getId())).map(user -> {
					try {
						user.setPassword(request.getUser().getPassword());
						userRepository.save(user);
						mailTokenRepository.delete(mailToken);
						return jsonPrepare.prepareResponse(StatusMessage.INFO, "RESTARTED", null, null, true);
					} catch (Exception e) {
						LOG.error(e.toString());
						return jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false);
					}
				}).orElseGet(
						() -> jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
			} else
				return jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false);
		}).orElseGet(() -> jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
	}

	@Override
	public Response<User> confirmRegister(Request request) {
		return mailTokenRepository.findByToken(request.getToken()).map(mailToken -> {
			if (userPredicate.validMailToken.test(mailToken, request)) {
				return Optional.ofNullable(userRepository.findOne(mailToken.getUser().getId())).map(user -> {
					user.setBlock(false);
					userRepository.save(user);
					mailTokenRepository.delete(mailToken);
					return jsonPrepare.prepareResponse(StatusMessage.SUCCESS, "SUCCESS_REGISTERED", null, null, true);
				}).orElseGet(
						() -> jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
			} else
				return jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false);
		}).orElseGet(() -> jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));

	}

	@Override
	public Response<User> changePassword(Request request) {
		try {
			return Optional.ofNullable(userRepository.findOne(request.getUser().getId())).map(user -> {
				user.setPassword(request.getUser().getPassword());
				userRepository.save(user);
				return jsonPrepare.prepareResponse(StatusMessage.SUCCESS, "SUCCESS_UPDATED", user, null, true);
			}).orElseGet(() -> jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
		} catch (Exception e) {
			LOG.error(e.toString());
			return jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false);
		}
	}

	@Override
	public Response<User> update(Request request) {
		try {
			return Optional.ofNullable(userRepository.findOne(request.getUser().getId())).map(user -> {
				Optional.ofNullable(request.getUser().getName()).ifPresent(y -> user.setName(y));
				Optional.ofNullable(request.getUser().getMail()).ifPresent(y -> user.setMail(y));
				user.setLastActionTime(LocalDateTime.now());
				userRepository.save(user);
				return jsonPrepare.prepareResponse(StatusMessage.SUCCESS, "SUCCESS_UPDATED", user, null, true);
			}).orElseGet(() -> jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
		} catch (PersistenceException e) {
			LOG.error(e.toString());
			if (e.getCause() instanceof ConstraintViolationException)
				return jsonPrepare.prepareResponse(StatusMessage.ERROR, "UNIQUE_NAME_OR_EMAIL", null, null, false);
			else
				return jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false);
		} catch (Exception e) {
			LOG.error(e.toString());
			return jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false);
		}
	}

	@Override
	public Response<User> getUser(Request request) {
		User userGet = Optional.ofNullable(request.getUser()).map(user -> userRepository.findOne(user.getId()))
				.orElseGet(() -> userRepository.findByToken(request.getToken()).get());

		return Optional.ofNullable(userGet).map(user -> {
			user.setToken(null);
			return jsonPrepare.prepareResponse(null, null, user, null, true);
		}).orElseGet(() -> jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
	}

	@Override
	public Response<User> logout(Request request) {
		return userRepository.findByToken(request.getToken()).map(user -> {
			user.setToken(null);
			user.setLastActionTime(null);
			userRepository.save(user);
			return jsonPrepare.prepareResponse(StatusMessage.SUCCESS, "LOGOUT", null, null, true);
		}).orElseGet(() -> jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
	}

	@Override
	public Response<User> getUsers(Request request) {
		if (commonPredicate.validatePageAndPageSize.test(request.getPage(), request.getSizePage()))
			return Optional
					.ofNullable(userRepository.findAll(new PageRequest(request.getPage(), request.getSizePage())))
					.map(users -> jsonPrepare.prepareResponse(null, null, null, users.getContent(), true))
					.orElseGet(() -> jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null,
							false));
		else
			return jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false);
	}

	@Override
	public Response<User> delete(Long id) {
		return Optional.ofNullable(userRepository.findOne(id)).map(user -> {
			user.setDeleted(true);
			userRepository.save(user);
			return jsonPrepare.prepareResponse(StatusMessage.SUCCESS, "DELTED", user, null, true);
		}).orElseGet(() -> jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
	}

	@Override
	public Response<User> block(Request request) {
		return Optional.ofNullable(userRepository.findOne(request.getUser().getId())).map(user -> {
			user.setBlock(request.getUser().isBlock());
			userRepository.save(user);
			if (request.getUser().isBlock())
				return jsonPrepare.prepareResponse(StatusMessage.SUCCESS, "BLOCKED", user, null, true);
			else
				return jsonPrepare.prepareResponse(StatusMessage.SUCCESS, "UNBLOCKED", user, null, true);
		}).orElseGet(() -> jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
	}

}
