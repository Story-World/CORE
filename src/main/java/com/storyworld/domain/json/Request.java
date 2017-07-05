package com.storyworld.domain.json;

import com.storyworld.domain.sql.User;

import lombok.Data;

@Data
public class Request {

	private String token;

	private User user;

	private int page;

	private int sizePage;

}
