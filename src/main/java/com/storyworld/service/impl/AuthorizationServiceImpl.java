package com.storyworld.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storyworld.domain.json.Request;
import com.storyworld.domain.sql.Comment;
import com.storyworld.domain.sql.User;
import com.storyworld.repository.sql.CommentRepository;
import com.storyworld.repository.sql.UserRepository;
import com.storyworld.service.AuthorizationService;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Override
	public boolean checkAccess(Request request) {
		Optional<User> user = userRepository.findByToken(request.getToken());
		return user.isPresent() && ChronoUnit.HOURS.between(user.get().getLastActionTime(), LocalDateTime.now()) <= 2;
	}

	@Override
	public boolean checkAccessToUser(Request request) {
		Optional<User> user = userRepository.findByToken(request.getToken());
		return user.isPresent() && ChronoUnit.HOURS.between(user.get().getLastActionTime(), LocalDateTime.now()) <= 2
				&& (request.getUser() == null || (user.get().getId() == request.getUser().getId()
						|| user.get().getRoles().removeIf(x -> x.getName().equals("ADMIN"))));
	}

	@Override
	public boolean checkAccessToComment(Request request) {
		Optional<User> user = userRepository.findByToken(request.getToken());
		Comment comment = commentRepository.findByAuthorAndStory(user.get(), request.getStory());
		return user.isPresent() && ChronoUnit.HOURS.between(user.get().getLastActionTime(), LocalDateTime.now()) <= 2
				&& (request.getComment() != null || request.getCommentContent() != null
						|| (comment.get_id().equals(request.getCommentContent().getId())
								|| comment.get_id().equals(request.getComment().get_id()))
						|| user.get().getRoles().removeIf(x -> x.getName().equals("ADMIN")));
	}

	@Override
	public boolean checkAccessAdmin(Request request) {
		Optional<User> user = userRepository.findByToken(request.getToken());
		return user.isPresent() && user.get().getRoles().removeIf(x -> x.getName().equals("ADMIN"));
	}

}
