package com.storyworld.domain.json;

import com.storyworld.domain.json.enums.StatusMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Message {

	private StatusMessage status;

	private String message;

}
