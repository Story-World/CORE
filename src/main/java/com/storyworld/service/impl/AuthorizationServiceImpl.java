package com.storyworld.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;
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

	private void authorizeToken(String token) {

	}

	@Override
	public Response prepareResponse(Request request) {
		authorizeToken(request.getToken());
		Response response = new Response();
		return response;
	}

	@Override
	public User checkAccessToUser(String token) {
		User user = userRepository.findByToken(token);
		// if(user==null)
		// throw new AccessDeniedException("AccesDenied");
		return user;
	}

	@Override
	public boolean checkAccess(Request request) {
		User user = userRepository.findByToken(request.getToken());
		return user != null && ChronoUnit.HOURS.between(user.getLastActionTime(), LocalDateTime.now()) <= 2;
	}

	@Override
	public boolean checkAccessToUser(Request request) {
		User user = userRepository.findByToken(request.getToken());
		return user != null && ChronoUnit.HOURS.between(user.getLastActionTime(), LocalDateTime.now()) <= 2
				&& (request.getUser() == null || (user.getId() == request.getUser().getId()
						|| user.getRoles().removeIf(x -> x.getName().equals("ADMIN"))));
	}

	@Override
	public boolean checkAccessToComment(Request request) {
		User user = userRepository.findByToken(request.getToken());
		Comment comment = commentRepository.findByAuthor(user);
		return user != null && ChronoUnit.HOURS.between(user.getLastActionTime(), LocalDateTime.now()) <= 2
				&& (request.getComment() != null || request.getCommentContent() != null
						|| (comment.get_id().equals(request.getCommentContent().getId())
								|| comment.get_id().equals(request.getComment().get_id()))
						|| user.getRoles().removeIf(x -> x.getName().equals("ADMIN")));
	}

}
