package com.packt.storyworld.repository.sql;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.packt.storyworld.domain.sql.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findByName(String name);

	public List<User> findByTokenNotNull();
}
