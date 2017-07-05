package com.storyworld.domain.json;

import com.storyworld.domain.json.enums.StatusMessage;

import lombok.Data;

@Data
public class Message {

	private StatusMessage status;

	private String message;

}
