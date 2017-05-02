package com.storyworld.service.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
import com.storyworld.domain.json.StatusMessage;
import com.storyworld.domain.sql.Comment;
import com.storyworld.domain.sql.Story;
import com.storyworld.domain.sql.User;
import com.storyworld.repository.sql.StoryRepository;
import com.storyworld.repository.elastic.CommentContentRepository;
import com.storyworld.repository.sql.CommentRepository;
import com.storyworld.repository.sql.UserRepository;
import com.storyworld.service.CommentService;
import com.storyworld.service.JSONService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private StoryRepository storyRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CommentContentRepository commentContentRepository;

	@Autowired
	private JSONService jsonService;

	private static final Logger LOG = LoggerFactory.getLogger(CommentServiceImpl.class);

	@Override
	public void get(Long idStory, int page, int pageSize, Response response) {
		Story story = storyRepository.findOne(idStory);
		if (story != null && page > -1 && pageSize > 0) {
			System.out.println(story.toString());
			System.out.println("ok");
			Page<Comment> comments = commentRepository.findByStory(story,
					new PageRequest(page, pageSize, new Sort(Direction.DESC, "date")));
			Set<CommentContent> commentsContent = new HashSet<>();
			for (Comment comment : comments)
				commentsContent.add(commentContentRepository.findOne(comment.get_id()));
			jsonService.prepareResponseForComment(response, null, null, commentsContent, true);
		} else
			jsonService.prepareErrorResponse(response, "INCORRECT_DATA");
	}

	@Override
	public void save(Request request, Response response) {
		User user = userRepository.findByToken(request.getToken());
		Story story = storyRepository.findOne(request.getStory().getId());
		CommentContent commentContent = request.getCommentContent();
		if (user != null && story != null && commentContent != null) {
			Comment comment = new Comment(user, story);
			try {
				user.setRoles(null);
				user.setLastIncorrectLogin(null);
				user.setLastActionTime(null);
				user.setToken(null);
				user.setMail(null);
				commentContent.setAuthor(user);
				commentContent.setLikes(0);
				commentContent.setDislikes(0);
				commentContent = commentContentRepository.save(commentContent);
				comment.set_id(commentContent.getId());
				comment.setDate(LocalDateTime.now());
				commentRepository.save(comment);
				jsonService.prepareResponseForComment(response, StatusMessage.SUCCESS, "ADDED", null, true);
			} catch (Exception e) {
				LOG.error(e.getMessage());
				jsonService.prepareErrorResponse(response, "INCORRECT_DATA");
			}
		} else
			jsonService.prepareErrorResponse(response, "INCORRECT_DATA");
	}

	@Override
	public void update(Request request, Response response) {
		User user = userRepository.findByToken(request.getToken());
		Story story = storyRepository.findOne(request.getStory().getId());
		Comment comment = commentRepository.findByAuthorAndStory(user, story);
		if (user != null && comment != null && request.getCommentContent() != null) {
			CommentContent commentContent = commentContentRepository.findOne(comment.get_id());
			commentContent.setContent(request.getCommentContent().getContent());
			comment.setDate(LocalDateTime.now());
			commentContentRepository.save(commentContent);
			commentRepository.save(comment);
			jsonService.prepareResponseForComment(response, StatusMessage.SUCCESS, "UPDATED", null, true);
		} else
			jsonService.prepareErrorResponse(response, "INCORRECT_DATA");
	}

	@Override
	public void delete(Long id, Response response) {
		Comment comment = commentRepository.findOne(id);
		if (comment != null) {
			CommentContent commentContent = commentContentRepository.findOne(comment.get_id());
			commentContentRepository.delete(commentContent);
			commentRepository.delete(comment);
			jsonService.prepareResponseForComment(response, StatusMessage.SUCCESS, "DELETED", null, true);
		} else
			jsonService.prepareErrorResponse(response, "INCORRECT_DATA");
	}

}
