package com.storyworld.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.persistence.PersistenceException;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.storyworld.domain.elastic.CommentContent;
import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;
import com.storyworld.domain.json.enums.StatusMessage;
import com.storyworld.domain.sql.Comment;
import com.storyworld.domain.sql.Mail;
import com.storyworld.domain.sql.MailToken;
import com.storyworld.domain.sql.Role;
import com.storyworld.domain.sql.Story;
import com.storyworld.domain.sql.User;
import com.storyworld.domain.sql.enums.Status;
import com.storyworld.domain.sql.enums.TypeToken;
import com.storyworld.repository.elastic.CommentContentRepository;
import com.storyworld.repository.sql.CommentRepository;
import com.storyworld.repository.sql.MailReposiotory;
import com.storyworld.repository.sql.MailTokenRepository;
import com.storyworld.repository.sql.RoleRepository;
import com.storyworld.repository.sql.StoryRepository;
import com.storyworld.repository.sql.UserRepository;
import com.storyworld.service.JSONService;
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
	private StoryRepository storyRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private CommentContentRepository commentContentRepository;

	@Autowired
	private JSONService jsonService;

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
	public Response login(Request request) {
		return userRepository.findByName(request.getUser().getName()).map(user -> {
			if (user.getName().equals(request.getUser().getName())
					&& user.getPassword().equals(request.getUser().getPassword())
					&& (!user.isBlock() || !user.isBlock() || (user.getLastIncorrectLogin() != null
							&& ChronoUnit.MINUTES.between(user.getLastIncorrectLogin(), LocalDateTime.now()) >= 10))) {
				user.setToken(UUID.randomUUID().toString());
				user.setLastActionTime(LocalDateTime.now());
				user.setBlock(false);
				user.setIncorrectLogin(0);
				user.setLastIncorrectLogin(null);
				userRepository.save(user);
				return jsonService.prepareResponseForUser(StatusMessage.SUCCESS, "LOGIN", user, null, true);
			} else {
				int incrementIncorrect = user.getIncorrectLogin();
				incrementIncorrect++;
				user.setIncorrectLogin(incrementIncorrect);
				if (user.getIncorrectLogin() == 5) {
					user.setBlock(true);
					user.setLastIncorrectLogin(LocalDateTime.now());
				}
				userRepository.save(user);
				return jsonService.prepareErrorResponse("INCORRECT_DATA");
			}
		}).orElseGet(() -> jsonService.prepareErrorResponse("INCORRECT_DATA"));
	}

	@Override
	public Response register(Request request) {
		try {
			return Optional.ofNullable(request.getUser()).map(user -> {
				user.setBlock(true);
				user.setDeleted(false);
				User userRegister = userRepository.save(user);
				Role role = roleRepository.findOne((long) 1);
				Set<Role> roles = new HashSet<>();
				roles.add(role);
				userRegister.setRoles(roles);
				userRepository.save(userRegister);
				MailToken mailToken = new MailToken(TypeToken.REGISTER, UUID.randomUUID().toString(),
						LocalDateTime.now(), userRegister);
				Set<MailToken> tokens = new HashSet<>();
				tokens.add(mailToken);
				mailTokenRepository.save(tokens);
				Mail mail = new Mail(TypeToken.REGISTER, Status.READY, userRegister);
				mailReposiotory.save(mail);
				return jsonService.prepareResponseForUser(StatusMessage.SUCCESS, "REGISTER", null, null, true);
			}).orElseGet(() -> jsonService.prepareErrorResponse("1INCORRECT_DATA"));
		} catch (PersistenceException e) {
			LOG.error(e.toString());
			if (e.getCause() instanceof ConstraintViolationException)
				return jsonService.prepareErrorResponse("2UNIQUE_NAME_OR_EMAIL");
		} catch (Exception e) {
			LOG.error(e.toString());
			return jsonService.prepareErrorResponse("3INCORRECT_DATA");
		}
		return new Response();
	}

	@Override
	public Response restartPassword(Request request) {
		return userRepository.findByMail(request.getUser().getMail()).map(user -> {
			return mailTokenRepository.findByUserAndTypeToken(user, TypeToken.RESTART_PASSWORD).map(mailToken -> {
				long id = mailToken.getId();
				mailTokenRepository.findByUser(user).map(tokens -> {
					tokens.removeIf(x -> x.getId() == id);
					mailToken.setValidationTime(LocalDateTime.now());
					mailToken.setToken(UUID.randomUUID().toString());
					tokens.add(mailToken);
					mailTokenRepository.save(tokens);
					return jsonService.prepareResponseForUser(StatusMessage.INFO, "RESTARTED", null, null, true);
				}).orElseGet(() -> {
					MailToken mailToken1 = new MailToken(TypeToken.RESTART_PASSWORD, UUID.randomUUID().toString(),
							LocalDateTime.now(), user);
					mailTokenRepository.save(mailTokenRepository.findByUser(user).map(tokens -> {
						tokens.add(mailToken1);
						return tokens;
					}).get());
					return jsonService.prepareResponseForUser(StatusMessage.INFO, "RESTARTED", null, null, true);
				});
				Mail mail = new Mail(TypeToken.RESTART_PASSWORD, Status.READY, user);
				mailReposiotory.save(mail);
				return jsonService.prepareResponseForUser(StatusMessage.INFO, "RESTARTED", null, null, true);
			}).orElseGet(() -> jsonService.prepareErrorResponse("INCORRECT_DATA"));
		}).orElseGet(() -> jsonService.prepareErrorResponse("INCORRECT_DATA"));
	}

	@Override
	public Response remindPassword(Request request) {
		return mailTokenRepository.findByToken(request.getToken()).map(mailToken -> {
			if (mailToken.getTypeToken().equals(TypeToken.RESTART_PASSWORD)
					&& ChronoUnit.DAYS.between(mailToken.getValidationTime(), LocalDateTime.now()) <= 1
					&& mailToken.getToken().equals(request.getToken())) {
				return Optional.ofNullable(userRepository.findOne(mailToken.getUser().getId())).map(user -> {
					try {
						user.setPassword(request.getUser().getPassword());
						userRepository.save(user);
						mailTokenRepository.delete(mailToken);
						return jsonService.prepareResponseForUser(StatusMessage.INFO, "RESTARTED", null, null, true);
					} catch (Exception e) {
						LOG.error(e.toString());
						return jsonService.prepareErrorResponse("INCORRECT_DATA");
					}
				}).orElseGet(() -> jsonService.prepareErrorResponse("INCORRECT_DATA"));
			} else
				return jsonService.prepareErrorResponse("INCORRECT_DATA");
		}).orElseGet(() -> jsonService.prepareErrorResponse("INCORRECT_DATA"));
	}

	@Override
	public Response confirmRegister(Request request) {
		return mailTokenRepository.findByToken(request.getToken()).map(mailToken -> {
			if (mailToken.getTypeToken().equals(TypeToken.REGISTER)
					&& mailToken.getToken().equals(request.getToken())) {
				return Optional.ofNullable(userRepository.findOne(mailToken.getUser().getId())).map(user -> {
					user.setBlock(false);
					userRepository.save(user);
					mailTokenRepository.delete(mailToken);
					return jsonService.prepareResponseForUser(StatusMessage.SUCCESS, "SUCCESS_REGISTERED", null, null,
							true);
				}).orElseGet(() -> jsonService.prepareErrorResponse("INCORRECT_DATA"));
			} else
				return jsonService.prepareErrorResponse("INCORRECT_DATA");
		}).orElseGet(() -> jsonService.prepareErrorResponse("INCORRECT_DATA"));

	}

	@Override
	public Response changePassword(Request request) {
		try {
			return Optional.ofNullable(userRepository.findOne(request.getUser().getId())).map(user -> {
				user.setPassword(request.getUser().getPassword());
				userRepository.save(user);
				return jsonService.prepareResponseForUser(StatusMessage.SUCCESS, "SUCCESS_UPDATED", user, null, true);
			}).orElseGet(() -> jsonService.prepareErrorResponse("INCORRECT_DATA"));
		} catch (Exception e) {
			LOG.error(e.toString());
			return jsonService.prepareErrorResponse("INCORRECT_DATA");
		}
	}

	@Override
	public Response update(Request request) {
		try {
			return Optional.ofNullable(userRepository.findOne(request.getUser().getId())).map(user -> {
				Optional.ofNullable(request.getUser().getName()).ifPresent(y -> user.setName(y));
				Optional.ofNullable(request.getUser().getMail()).ifPresent(y -> user.setMail(y));
				user.setLastActionTime(LocalDateTime.now());
				userRepository.save(user);
				return jsonService.prepareResponseForUser(StatusMessage.SUCCESS, "SUCCESS_UPDATED", user, null, true);
			}).orElseGet(() -> jsonService.prepareErrorResponse("INCORRECT_DATA"));
		} catch (PersistenceException e) {
			LOG.error(e.toString());
			if (e.getCause() instanceof ConstraintViolationException)
				return jsonService.prepareErrorResponse("UNIQUE_NAME_OR_EMAIL");
		} catch (Exception e) {
			LOG.error(e.toString());
			return jsonService.prepareErrorResponse("INCORRECT_DATA");
		}
		return new Response();
	}

	@Override
	public Response getUser(Request request) {
		User userGet = Optional.ofNullable(request.getUser()).map(user -> userRepository.findOne(user.getId()))
				.orElseGet(() -> userRepository.findByToken(request.getToken()).get());

		return Optional.ofNullable(userGet).map(user -> {
			user.setToken(null);
			return jsonService.prepareResponseForUser(null, null, user, null, true);
		}).orElseGet(() -> jsonService.prepareErrorResponse("INCORRECT_DATA"));
	}

	@Override
	public Response logout(Request request) {
		return userRepository.findByToken(request.getToken()).map(user -> {
			user.setToken(null);
			user.setLastActionTime(null);
			userRepository.save(user);
			return jsonService.prepareResponseForUser(StatusMessage.SUCCESS, "LOGOUT", null, null, true);
		}).orElseGet(() -> jsonService.prepareErrorResponse("INCORRECT_DATA"));
	}

	@Override
	public Response getUsers(Request request) {
		if (request.getPage() > -1 && request.getSizePage() > 0)
			return Optional
					.ofNullable(userRepository.findAll(new PageRequest(request.getPage(), request.getSizePage())))
					.map(users -> jsonService.prepareResponseForUser(null, null, null, users.getContent(), true))
					.orElseGet(() -> jsonService.prepareErrorResponse("INCORRECT_DATA"));
		else
			return jsonService.prepareErrorResponse("INCORRECT_DATA");

	}

	@Override
	public Response delete(Long id) {
		return Optional.ofNullable(userRepository.findOne(id)).map(user -> {
			user.setDeleted(true);
			userRepository.save(user);
			return jsonService.prepareResponseForUser(StatusMessage.SUCCESS, "DELTED", user, null, true);
		}).orElseGet(() -> jsonService.prepareErrorResponse("INCORRECT_DATA"));
	}

	@Override
	public Response get(Long id) {
		return Optional.ofNullable(userRepository.findOne(id)).map(user -> {
			Page<Story> stories = storyRepository.findByAuthor(user,
					new PageRequest(0, 20, new Sort(Direction.DESC, "date")));
			Page<Comment> comments = commentRepository.findByAuthor(user, new PageRequest(0, 20));
			List<CommentContent> commentsContent = new LinkedList<>();
			comments.forEach(x -> commentsContent.add(commentContentRepository.findOne(x.get_id())));
			commentsContent.sort((CommentContent o1, CommentContent o2) -> o2.getDate().compareTo(o1.getDate()));
			return jsonService.prepareResponse(commentsContent, stories.getContent(), user, true);
		}).orElseGet(() -> jsonService.prepareErrorResponse("INCORRECT_DATA"));
	}

	@Override
	public Response block(Request request) {
		return Optional.ofNullable(userRepository.findOne(request.getUser().getId())).map(user -> {
			user.setBlock(request.getUser().isBlock());
			userRepository.save(user);
			if (request.getUser().isBlock())
				return jsonService.prepareResponseForUser(StatusMessage.SUCCESS, "BLOCKED", user, null, true);
			else
				return jsonService.prepareResponseForUser(StatusMessage.SUCCESS, "UNBLOCKED", user, null, true);
		}).orElseGet(() -> jsonService.prepareErrorResponse("INCORRECT_DATA"));
	}

}
