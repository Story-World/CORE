package com.storyworld.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;

import com.storyworld.domain.sql.RestartPassword;

public interface RestartPasswordRepository extends JpaRepository<RestartPassword, Long> {

	public RestartPassword findByToken(String token);

}
