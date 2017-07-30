package com.storyworld.service;

import java.util.List;

import com.storyworld.domain.elastic.CommentContent;
import com.storyworld.domain.elastic.StoryContent;
import com.storyworld.domain.json.Response;
import com.storyworld.domain.json.enums.StatusMessage;
import com.storyworld.domain.sql.Story;
import com.storyworld.domain.sql.User;

public interface JSONService {

	public void prepareResponseForUser(Response response, StatusMessage messageStatus, String messageString, User user,
			List<User> users, boolean success);

	public void prepareResponseForComment(Response response, StatusMessage messageStatus, String messageString,
			List<CommentContent> commentsContent, CommentContent comment, boolean success);

	public void prepareResponse(Response response, List<CommentContent> comments, List<Story> stories, User user,
			boolean success);

	public void prepareErrorResponse(Response response, String messageString);

	public void prepareSimpleResponse(Response response, boolean success, StatusMessage status, String messageText);

	public Response prepareResponseForUser(StatusMessage messageStatus, String messageString, User user,
			List<User> users, boolean success);

	public Response prepareResponseForComment(StatusMessage messageStatus, String messageString,
			List<CommentContent> commentsContent, CommentContent comment, boolean success);

	public Response prepareResponse(List<CommentContent> comments, List<Story> stories, User user, boolean success);

	public Response prepareErrorResponse(String messageString);

	public Response prepareSimpleResponse(boolean success, StatusMessage status, String messageText);

	public Response prepareResponseForStroy(StatusMessage status, String messageText, Story story,
			StoryContent storyContent, List<Story> content, boolean success);

}
