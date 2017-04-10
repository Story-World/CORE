package com.storyworld.repository.sql;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.storyworld.domain.sql.Mail;
import com.storyworld.domain.sql.User;
import com.storyworld.enums.Status;

public interface MailReposiotory extends JpaRepository<Mail, Long> {

	public List<Mail> findByStatus(Status status);

	public Mail findByUser(User user);

}
