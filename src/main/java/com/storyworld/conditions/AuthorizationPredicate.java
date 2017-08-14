package com.storyworld.conditions;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.function.BiPredicate;

import org.springframework.stereotype.Component;

import com.storyworld.domain.json.Request;
import com.storyworld.domain.sql.User;

@Component
public class AuthorizationPredicate {

	public BiPredicate<Optional<User>, Long> checkAccess = (user, time) -> user.isPresent()
			&& ChronoUnit.HOURS.between(user.get().getLastActionTime(), LocalDateTime.now()) <= time;

	public BiPredicate<Optional<User>, String> checkRole = (user, role) -> user.isPresent()
			&& user.get().getRoles().removeIf(x -> x.getName().equals(role));

	public BiPredicate<Optional<User>, Request> checkAccessToUser = (user, request) -> checkAccess.test(user, 2L)
			&& (request.getUser() == null
					|| (user.get().getId() == request.getUser().getId() || checkRole.test(user, "ADMIN")));

}
