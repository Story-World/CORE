package com.storyworld.domain.json;

import com.storyworld.domain.json.enums.StatusMessage;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message {

	private StatusMessage status;

	private String message;

}
