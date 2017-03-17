package com.storyworld.repository.sql;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.storyworld.domain.sql.RestartPassword;
import com.storyworld.domain.sql.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findById(long id);

	public User findByName(String name);

	public User findByToken(String token);

	public List<User> findByTokenNotNull();

	public User findByMail(String mail);

	public User findByRestartPassword(RestartPassword restartPassword);
}
