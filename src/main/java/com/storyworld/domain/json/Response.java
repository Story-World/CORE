package com.storyworld.domain.json;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
import lombok.ToString;
import lombok.NoArgsConstructor;

@Data
@ToString
@NoArgsConstructor
@SuppressWarnings("deprecation")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Response<T> {

	private Message message;

	private T t;

	private List<T> list;

	private boolean success;

	public Response(Message message, T t, List<T> listT, boolean success) {
		if (message.getStatus() == null)
			this.message = null;
		this.t = t;
		this.list = listT;
		this.success = success;
	}

}