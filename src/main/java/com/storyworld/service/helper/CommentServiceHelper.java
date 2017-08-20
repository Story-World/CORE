package com.storyworld.service.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.storyworld.domain.elastic.CommentContent;
import com.storyworld.domain.json.Message;
import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;
import com.storyworld.domain.json.enums.StatusMessage;
import com.storyworld.domain.sql.Comment;
import com.storyworld.domain.sql.Story;
import com.storyworld.domain.sql.User;
import com.storyworld.functionalInterface.JSONPrepare;
import com.storyworld.repository.elastic.CommentContentRepository;
import com.storyworld.repository.sql.CommentRepository;
import com.storyworld.repository.sql.StoryRepository;
import com.storyworld.repository.sql.UserRepository;

@Component
public class CommentServiceHelper {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private StoryRepository storyRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CommentContentRepository commentContentRepository;

	private JSONPrepare<CommentContent> jsonPrepare = (statusMessage, message, commentContent, list,
			success) -> new Response<CommentContent>(new Message(statusMessage, message), commentContent, list,
					success);

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	private static final Logger LOG = LoggerFactory.getLogger(CommentServiceHelper.class);

	public Response<CommentContent> prepareCommentContent(Story story, int page, int pageSize) {
		Page<Comment> comments = commentRepository.findByStory(story, new PageRequest(page, pageSize));
		List<CommentContent> commentsContent = new LinkedList<>();
		comments.forEach(x -> commentsContent.add(commentContentRepository.findOne(x.get_id())));
		commentsContent.sort((CommentContent o1, CommentContent o2) -> o2.getDate().compareTo(o1.getDate()));
		return jsonPrepare.prepareResponse(null, null, null, commentsContent, true);
	}

	public synchronized Response<CommentContent> tryToSaveComment(User user, Story story, CommentContent commentContent) {
		Comment commentSave = new Comment(user, story);
		try {
			user.setLastActionTime(LocalDateTime.now());
			userRepository.save(user);
			user = new User(user.getId(), user.getName());
			commentContent.setAuthor(user);
			commentContent.setStoryId(story.getId());
			commentContent.setLikes(0);
			commentContent.setDislikes(0);
			commentContent.setDate(LocalDateTime.now().format(FORMATTER).toString());
			commentContent = commentContentRepository.save(commentContent);
			commentSave.set_id(commentContent.getId());
			commentRepository.save(commentSave);
			return jsonPrepare.prepareResponse(StatusMessage.SUCCESS, "ADDED2", commentContent, null, true);
		} catch (Exception e) {
			LOG.error(e.toString());
			return jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA3", null, null, false);
		}
	}

	public Response<CommentContent> prepareToSaveComment(Request request, User user) {
		Story story = storyRepository.findOne(request.getStory().getId());
		Optional<Comment> comment = commentRepository.findByAuthorAndStory(user, story);
		Optional<CommentContent> commentContent = Optional.ofNullable(request.getCommentContent());
		System.out.println();
		return Optional.ofNullable(story).isPresent() && commentContent.isPresent() && !comment.isPresent()
				? tryToSaveComment(user, story, commentContent.get())
				: Optional.ofNullable(comment).isPresent()
						? jsonPrepare.prepareResponse(StatusMessage.ERROR, "UNIQUE_COMMENT", null, null, false)
						: jsonPrepare.prepareResponse(StatusMessage.ERROR, "INCORRECT_DATA", null, null, false);
	}

	public synchronized Response<CommentContent> updateCommentContent(Optional<Comment> comment, User user,
			CommentContent commentContentRequest) {
		CommentContent commentContent = commentContentRepository.findOne(comment.get().get_id());
		commentContent.setEdited(true);
		commentContent.setContent(commentContentRequest.getContent());
		commentContent.setDate(LocalDateTime.now().format(FORMATTER).toString());
		commentContent = commentContentRepository.save(commentContent);
		user.setLastActionTime(LocalDateTime.now());
		userRepository.save(user);
		return jsonPrepare.prepareResponse(StatusMessage.SUCCESS, "UPDATED", commentContent, null, true);
	}

	public synchronized Response<CommentContent> deleteComment(Optional<Comment> comment, Optional<User> user) {
		CommentContent commentContent = commentContentRepository.findOne(comment.get().get_id());
		commentContentRepository.delete(commentContent);
		commentRepository.delete(comment.get());
		user.get().setLastActionTime(LocalDateTime.now());
		userRepository.save(user.get());
		return jsonPrepare.prepareResponse(StatusMessage.SUCCESS, "DELETED", null, null, true);
	}

	public Response<CommentContent> prepareComments(User user) {
		List<Comment> comments = commentRepository.f
		return null;
	}

}
