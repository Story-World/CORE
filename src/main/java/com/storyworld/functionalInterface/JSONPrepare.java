package com.storyworld.functionalInterface;

import java.util.List;

import com.storyworld.domain.json.Response;
import com.storyworld.domain.json.enums.StatusMessage;

@FunctionalInterface
public interface JSONPrepare<T> {

	public Response<T> prepareResponse(StatusMessage messageStatus, String messageString, T t, List<T> list,
			boolean success);

}
