package com.storyworld.domain.json;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import com.storyworld.domain.elastic.CommentContent;
import com.storyworld.domain.elastic.StoryContent;
import com.storyworld.domain.sql.Comment;
import com.storyworld.domain.sql.Story;
import com.storyworld.domain.sql.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("deprecation")
@Data
@EqualsAndHashCode
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Response {

	private boolean success;

	private Message message;

	private User user;

	private List<User> users;

	private List<CommentContent> comments;

	private CommentContent comment;

	private Story story;

	private StoryContent storyContent;

	private List<Story> stories;

	private List<Comment> commentsList;

}
