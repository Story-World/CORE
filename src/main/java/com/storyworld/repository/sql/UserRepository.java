package com.storyworld.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;

import com.storyworld.domain.sql.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
