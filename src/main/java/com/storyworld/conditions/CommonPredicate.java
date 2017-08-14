package com.storyworld.conditions;

import java.util.Optional;
import java.util.function.BiPredicate;

import org.springframework.stereotype.Component;

@Component
public class CommonPredicate {

	public BiPredicate<Integer, Integer> validatePageAndPageSize = (page,
			pageSize) -> Optional.ofNullable(page).isPresent() && Optional.ofNullable(pageSize).isPresent() && page > -1
					&& pageSize > 0;

}
