package com.storyworld.domain.json;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Response {

	private boolean success;

}
