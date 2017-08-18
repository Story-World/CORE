package com.storyworld.service.impl;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storyworld.conditions.CommonPredicate;
import com.storyworld.conditions.UserPredicate;
import com.storyworld.domain.json.Message;
import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;
import com.storyworld.domain.json.enums.StatusMessage;
import com.storyworld.domain.sql.User;
import com.storyworld.functionalInterface.JSONPrepare;
import com.storyworld.repository.sql.MailTokenRepository;
import com.storyworld.repository.sql.UserRepository;
import com.storyworld.service.UserService;
import com.storyworld.service.helper.UserServiceHelper;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MailTokenRepository mailTokenRepository;

	@Autowired
	private UserPredicate userPredicate;

	@Autowired
	private CommonPredicate commonPredicate;

	@Autowired
	private UserServiceHelper userServiceHelper;

	private JSONPrepare<User> jsonPrepare = (statusMessage, message, user, list,
			success) -> new Response<User>(new Message(statusMessage, message), user, list, success);

	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

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
		return userRepository.findByName(request.getUser().getName())
				.map(user -> userPredicate.vaildUserLogin.test(user, request) ? userServiceHelper.successLogin(user)
						: userServiceHelper.unsuccessLogin(user))
				.orElse(jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
	}

	@Override
	public Response<User> register(Request request) {
		try {
			return Optional.ofNullable(request.getUser()).map(user -> userServiceHelper.prepareUserToSave(user))
					.orElse(jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
		} catch (PersistenceException e) {
			LOG.error(e.toString());
			// return e.getCause() instanceof ConstraintViolationException
			// ? jsonPrepare.prepareResponse(StatusMessage.ERROR,
			// "UNIQUE_NAME_OR_EMAIL", null, null, false)
			// :
			return jsonPrepare.prepareResponse(StatusMessage.ERROR, "UNIQUE_NAME_OR_EMAIL", null, null, false);
		} catch (Exception e) {
			LOG.error(e.toString());
			return jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false);
		}
	}

	@Override
	public Response<User> restartPassword(Request request) {
		return userRepository.findByMail(request.getUser().getMail())
				.map(user -> userServiceHelper.addMailToMailerAfterRestartPassword(user))
				.orElse(jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
	}

	@Override
	public Response<User> remindPassword(Request request) {
		return mailTokenRepository.findByToken(request.getToken())
				.map(mailToken -> userServiceHelper.changePasswordIfMailTokenIsValid(request, mailToken))
				.orElse(jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
	}

	@Override
	public Response<User> confirmRegister(Request request) {
		return mailTokenRepository.findByToken(request.getToken())
				.map(mailToken -> userPredicate.validMailToken.test(mailToken, request)
						? userServiceHelper.confirmRegisterInDB(mailToken)
						: jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false))
				.orElse(jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
	}

	@Override
	public Response<User> changePassword(Request request) {
		try {
			return Optional.ofNullable(userRepository.findOne(request.getUser().getId()))
					.map(user -> userServiceHelper.updateUserPassword(request.getUser().getPassword(), user, null))
					.orElse(jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
		} catch (Exception e) {
			LOG.error(e.toString());
			return jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false);
		}
	}

	@Override
	public Response<User> update(Request request) {
		try {
			return Optional.ofNullable(userRepository.findOne(request.getUser().getId()))
					.map(user -> userServiceHelper.updateUserNameOrMail(request.getUser(), user))
					.orElse(jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
		} catch (PersistenceException e) {
			LOG.error(e.toString());
			return e.getCause() instanceof ConstraintViolationException
					? jsonPrepare.prepareResponse(StatusMessage.ERROR, "UNIQUE_NAME_OR_EMAIL", null, null, false)
					: jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false);
		} catch (Exception e) {
			LOG.error(e.toString());
			return jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false);
		}
	}

	@Override
	public Response<User> getUser(Request request) {
		User userGet = Optional.ofNullable(request.getUser()).map(user -> userRepository.findOne(user.getId()))
				.orElse(userRepository.findByToken(request.getToken()).get());

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
		}).orElse(jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
	}

	@Override
	public Response<User> getUsers(Request request) {
		return commonPredicate.validatePageAndPageSize.test(request.getPage(), request.getSizePage())
				? userServiceHelper.getUsersFromDB(request.getPage(), request.getSizePage())
				: jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false);
	}

	@Override
	public Response<User> delete(Long id) {
		return Optional.ofNullable(userRepository.findOne(id)).map(user -> {
			user.setDeleted(true);
			userRepository.save(user);
			return jsonPrepare.prepareResponse(StatusMessage.SUCCESS, "DELTED", user, null, true);
		}).orElse(jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
	}

	@Override
	public Response<User> block(Request request) {
		return Optional.ofNullable(userRepository.findOne(request.getUser().getId())).map(user -> {
			user.setBlock(request.getUser().isBlock());
			userRepository.save(user);
			return request.getUser().isBlock()
					? jsonPrepare.prepareResponse(StatusMessage.SUCCESS, "BLOCKED", user, null, true)
					: jsonPrepare.prepareResponse(StatusMessage.SUCCESS, "UNBLOCKED", user, null, true);
		}).orElse(jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false));
	}

}
