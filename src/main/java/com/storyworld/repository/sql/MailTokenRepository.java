package com.storyworld.repository.sql;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.storyworld.domain.sql.MailToken;
import com.storyworld.domain.sql.User;

public interface MailTokenRepository extends JpaRepository<MailToken, Long> {

	public MailToken findByToken(String token);

	public Set<MailToken> findByUser(User user);
}
